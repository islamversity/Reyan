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

            listOf(LicensesRepoModel(1,"s", "s"))

        }
    }
}