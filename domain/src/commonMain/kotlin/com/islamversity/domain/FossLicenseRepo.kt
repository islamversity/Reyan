package com.islamversity.domain

import com.islamversity.domain.model.LicenseRepoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.CoroutineContext


interface FossLicenseRepo {

    fun getAllFossLicenses(context: CoroutineContext = Dispatchers.Default): Flow<LicenseRepoModel>

}

class FossLicenseRepoImpl : FossLicenseRepo {
    override fun getAllFossLicenses(context: CoroutineContext): Flow<LicenseRepoModel> {
        return flow {

            listOf(LicenseRepoModel("s", "s"))

        }
    }
}