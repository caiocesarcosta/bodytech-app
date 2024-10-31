package com.example.bodytech.di

import com.example.bodytech.viewmodel.company.CompanyViewModel
import com.example.bodytech.viewmodel.company.CompanyViewModelImpl
import com.example.bodytech.viewmodel.user.UserViewModel
import com.example.bodytech.viewmodel.user.UserViewModelImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelUserModule {
    @Binds
    @ViewModelScoped
    abstract fun bindUserViewModel(userViewModelImpl: UserViewModelImp): UserViewModel

    @Binds
    @ViewModelScoped
    abstract fun bindCompanyViewModel(companyViewModelImpl: CompanyViewModelImpl): CompanyViewModel
}