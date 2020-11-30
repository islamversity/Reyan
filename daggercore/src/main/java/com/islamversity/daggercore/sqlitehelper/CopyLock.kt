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

import androidx.annotation.RestrictTo
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import java.util.*
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * Utility class for in-process and multi-process key-based lock mechanism for safely copying
 * database files.
 *
 *
 * Acquiring the lock will be quick if no other thread or process has a lock with the same key.
 * But if the lock is already held then acquiring it will block, until the other thread or process
 * releases the lock. Note that the key and lock directory must be the same to achieve
 * synchronization.
 *
 *
 * Locking is done via two levels:
 *
 *  1.
 * Thread locking within the same JVM process is done via a map of String key to ReentrantLock
 * objects.
 *  1.
 * Multi-process locking is done via a lock file whose name contains the key and FileLock
 * objects.
 *
 * @hide
 */
class CopyLock(name: String, lockDir: File, processLock: Boolean) {
    private val mCopyLockFile: File = File(lockDir, "$name.lck")
    private val mThreadLock: Lock
    private val mFileLevelLock: Boolean
    private var mLockChannel: FileChannel? = null


    /**
     * Creates a lock with `name` and using `lockDir` as the directory for the
     * lock files.
     * @param name the name of this lock.
     * @param lockDir the directory where the lock files will be located.
     * @param processLock whether to use file for process level locking or not.
     */
    init {
        mThreadLock = getThreadLock(mCopyLockFile.absolutePath)
        mFileLevelLock = processLock
    }

    /**
     * Attempts to grab the lock, blocking if already held by another thread or process.
     */
    fun lock() {
        mThreadLock.lock()
        if (mFileLevelLock) {
            try {
                mLockChannel = FileOutputStream(mCopyLockFile).channel
                mLockChannel!!.lock()
            } catch (e: IOException) {
                throw IllegalStateException("Unable to grab copy lock.", e)
            }
        }
    }

    /**
     * Releases the lock.
     */
    fun unlock() {
        if (mLockChannel != null) {
            try {
                mLockChannel!!.close()
            } catch (ignored: IOException) {
            }
        }
        mThreadLock.unlock()
    }
}

// in-process lock map
private val sThreadLocks: MutableMap<String, Lock> =
    HashMap()

private fun getThreadLock(key: String): Lock {
    synchronized(sThreadLocks) {
        var threadLock = sThreadLocks[key]
        if (threadLock == null) {
            threadLock = ReentrantLock()
            sThreadLocks[key] = threadLock
        }
        return threadLock
    }
}