package com.example.bodytech.di

import com.example.bodytech.repository.auth.AuthRepository
import com.example.bodytech.repository.auth.AuthRepositoryImp
import com.example.bodytech.repository.company.remote.CompanyRepository
import com.example.bodytech.repository.company.remote.CompanyRepositoryImpl
import com.example.bodytech.repository.user.remote.UserRepository
import com.example.bodytech.repository.user.remote.UserRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt que vincula interfaces de repositório às suas implementações concretas.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImp: AuthRepositoryImp
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImp: UserRepositoryImp
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindCompanyRepository(
        companyRepositoryImp: CompanyRepositoryImpl
    ): CompanyRepository
}