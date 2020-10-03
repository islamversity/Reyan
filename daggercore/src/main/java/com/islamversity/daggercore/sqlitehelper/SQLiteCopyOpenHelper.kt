/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.islamversity.daggercore.sqlitehelper

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import java.io.*
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.FileChannel
import java.nio.channels.ReadableByteChannel
import java.util.concurrent.Callable
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

private const val LOG_TAG = "SQLiteCopyOpenHelper"

/**
 * An open helper that will copy & open a pre-populated database if it doesn't exists in internal
 * storage.
 */
class SQLiteCopyOpenHelper(
    private val mContext: Context,
    private val mCopyFromAssetPath: String?,
    private val mCopyFromFile: File?,
    private val mCopyFromInputStream: Callable<InputStream>?,
    private val mDatabaseVersion: Int,
    private val mDelegate: SupportSQLiteOpenHelper
) : SupportSQLiteOpenHelper {
    private var mVerified = false
    override fun getDatabaseName(): String? {
        return mDelegate.databaseName
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        mDelegate.setWriteAheadLoggingEnabled(enabled)
    }

    @Synchronized
    override fun getWritableDatabase(): SupportSQLiteDatabase {
        if (!mVerified) {
            verifyDatabaseFile()
            mVerified = true
        }
        return mDelegate.writableDatabase
    }

    @Synchronized
    override fun getReadableDatabase(): SupportSQLiteDatabase {
        if (!mVerified) {
            verifyDatabaseFile()
            mVerified = true
        }
        return mDelegate.readableDatabase
    }

    @Synchronized
    override fun close() {
        mDelegate.close()
        mVerified = false
    }

    private fun verifyDatabaseFile() {
        val databaseName = databaseName
        val databaseFile = mContext.getDatabasePath(databaseName)
        val copyLock = CopyLock(databaseName!!, mContext.filesDir, true)
        try {
            // Acquire a copy lock, this lock works across threads and processes, preventing
            // concurrent copy attempts from occurring.
            copyLock.lock()
            if (!databaseFile.exists()) {
                try {
                    // No database file found, copy and be done.
                    copyDatabaseFile(databaseFile)
                    return
                } catch (e: IOException) {
                    throw RuntimeException("Unable to copy database file.", e)
                }
            }

            // A database file is present, check if we need to re-copy it.
            val currentVersion: Int = try {
                readVersion(databaseFile)
            } catch (e: IOException) {
                Log.w(LOG_TAG, "Unable to read database version.", e)
                return
            }
            if (currentVersion == mDatabaseVersion) {
                return
            }
            if (currentVersion <= mDatabaseVersion) {
                // From the current version to the desired version a migration is required, i.e.
                // we won't be performing a copy destructive migration.
                return
            }
            if (mContext.deleteDatabase(databaseName)) {
                try {
                    copyDatabaseFile(databaseFile)
                } catch (e: IOException) {
                    // We are more forgiving copying a database on a destructive migration since
                    // there is already a database file that can be opened.
                    Log.w(LOG_TAG, "Unable to copy database file.", e)
                }
            } else {
                Log.w(
                    LOG_TAG, "Failed to delete database file (" +
                            databaseName + ") for a copy destructive migration."
                )
            }
        } finally {
            copyLock.unlock()
        }
    }

    private fun copyDatabaseFile(destinationFile: File) {
        val input: ReadableByteChannel
        if (mCopyFromAssetPath != null) {
            input = Channels.newChannel(mContext.assets.open(mCopyFromAssetPath))
        } else if (mCopyFromFile != null) {
            input = FileInputStream(mCopyFromFile).channel
        } else if (mCopyFromInputStream != null) {
            val inputStream: InputStream
            inputStream = try {
                mCopyFromInputStream.call()
            } catch (e: Exception) {
                throw IOException("inputStreamCallable exception on call", e)
            }
            input = Channels.newChannel(inputStream)
        } else {
            throw IllegalStateException(
                "copyFromAssetPath, copyFromFile and " +
                        "copyFromInputStream are all null!"
            )
        }

        // An intermediate file is used so that we never end up with a half-copied database file
        // in the internal directory.
        val intermediateFile = File.createTempFile(
            "room-copy-helper", ".tmp", mContext.cacheDir
        )
        intermediateFile.deleteOnExit()
        val output = FileOutputStream(intermediateFile).channel
        unpackZip(input, output)
//        copy(input, output)
        val parent = destinationFile.parentFile
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw IOException(
                "Failed to create directories for " +
                        destinationFile.absolutePath
            )
        }
        if (!intermediateFile.renameTo(destinationFile)) {
            throw IOException(
                "Failed to move intermediate file (" +
                        intermediateFile.absolutePath + ") to destination (" +
                        destinationFile.absolutePath + ")."
            )
        }
    }
}

/**
 * Reads the user version number out of the database header from the given file.
 *
 * @param databaseFile the database file.
 * @return the database version
 * @throws IOException if something goes wrong reading the file, such as bad database header or
 * missing permissions.
 *
 * @see [User Version
 * Number](https://www.sqlite.org/fileformat.html.user_version_number).
 */
private fun readVersion(databaseFile: File): Int {
    var input: FileChannel? = null
    return try {
        val buffer = ByteBuffer.allocate(4)
        input = FileInputStream(databaseFile).channel
        input.tryLock(60, 4, true)
        input.position(60)
        val read = input.read(buffer)
        if (read != 4) {
            throw IOException("Bad database header, unable to read 4 bytes at offset 60")
        }
        buffer.rewind()
        buffer.int // ByteBuffer is big-endian by default
    } finally {
        input?.close()
    }
}

/**
 * Copies data from the input channel to the output file channel.
 *
 * @param input  the input channel to copy.
 * @param output the output channel to copy.
 * @throws IOException if there is an I/O error.
 */
private fun copy(input: ReadableByteChannel, output: FileChannel) {
    try {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            output.transferFrom(input, 0, Long.MAX_VALUE)
        } else {
            val inputStream = Channels.newInputStream(input)
            val outputStream = Channels.newOutputStream(output)
            var length: Int
            val buffer = ByteArray(1024 * 4)
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
        }
        output.force(false)
    } finally {
        input.close()
        output.close()
    }
}

// this method can be much faster and more reliable
// also we can use OkIO for better performance
fun unpackZip(input: ReadableByteChannel, output: FileChannel): Boolean {
    ZipInputStream(Channels.newInputStream(input)).use { zis ->
        try {
            val ze: ZipEntry = zis.nextEntry
            val filename: String = ze.name

            val buffer = ByteArray(1024 * 4)
            var count: Int

            val fout = Channels.newOutputStream(output)

            while (zis.read(buffer).also { count = it } != -1) {
                fout.write(buffer, 0, count)
            }

            output.force(false)
            zis.closeEntry()
        } finally {
            output.close()
            input.close()
        }
    }
    return true
}
