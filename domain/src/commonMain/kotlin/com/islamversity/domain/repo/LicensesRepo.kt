 package com.islamversity.domain.repo

import com.islamversity.domain.model.LicensesRepoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.CoroutineContext


interface LicensesRepo {

    fun getAllLicenses(context: CoroutineContext = Dispatchers.Default): Flow<List<LicensesRepoModel>>

}

class LicensesRepoImpl : LicensesRepo {
    override fun getAllLicenses(context: CoroutineContext): Flow<List<LicensesRepoModel>> {
        return flow {

            emit(listOf(
                LicensesRepoModel(1,"Firebase", "https://github.com/firebase/firebase-android-sdk/blob/master/LICENSE"),
                LicensesRepoModel(2,"OkHttp", "https://github.com/square/okhttp/blob/master/LICENSE.txt"),
                LicensesRepoModel(3,"Yandex metrica ", "https://yandex.ru/legal/metrica_termsofuse/"),
                LicensesRepoModel(4,"Timber", "https://github.com/JakeWharton/timber/blob/master/LICENSE.txt"),
                LicensesRepoModel(5,"Dagger", "https://github.com/google/dagger/blob/master/LICENSE.txt"),
                LicensesRepoModel(6,"Coroutines", "https://github.com/Kotlin/kotlinx.coroutines/blob/master/LICENSE.txt"),
                LicensesRepoModel(7,"SQLDelight", "https://github.com/cashapp/sqldelight/blob/master/LICENSE.txt"),
                LicensesRepoModel(8,"Ktor", "https://github.com/ktorio/ktor/blob/main/LICENSE"),
                LicensesRepoModel(9,"Stately", "https://github.com/touchlab/Stately/blob/main/LICENSE.txt"),
                LicensesRepoModel(10,"Okio", "https://github.com/square/okio/blob/master/LICENSE.txt"),
                LicensesRepoModel(11,"Uuid", "https://github.com/benasher44/uuid/blob/master/LICENSE"),
                LicensesRepoModel(12,"Kermit", "https://github.com/touchlab/Kermit/blob/master/LICENSE.txt"),
                          ))

        }
    }
}