package com.example.bioimpedance.di

import com.example.bioimpedance.repository.BioImpedanceRepository
import com.example.bioimpedance.repository.BioImpedanceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBioImpedanceModule {

    @Singleton
    @Binds
    abstract fun bindBioImpedanceRepository(bioImpedanceRepositoryImpl: BioImpedanceRepositoryImpl): BioImpedanceRepository

}
