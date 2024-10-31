package com.example.bodytech.di

import com.example.bodytech.repository.company.CompanyRepository
import com.example.bodytech.repository.company.CompanyRepositoryImpl
import com.example.bodytech.repository.user.UserRepository
import com.example.bodytech.repository.user.UserRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryUserModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImp: UserRepositoryImp): UserRepository

    @Singleton
    @Binds
    abstract fun bindCompanyRepository(companyRepositoryImp: CompanyRepositoryImpl): CompanyRepository
}
