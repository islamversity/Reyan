package com.islamversity.daggercore.modules.domain

import com.islamversity.domain.repo.LicensesRepo
import com.islamversity.domain.repo.LicensesRepoImpl
import dagger.Module
import dagger.Provides

@Module
object LicensesRepoModule {

    @Provides
    @JvmStatic
    fun provideLicensesRepo () : LicensesRepo = LicensesRepoImpl()
}