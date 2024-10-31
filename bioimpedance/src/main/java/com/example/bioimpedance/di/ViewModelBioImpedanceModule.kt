package com.example.bioimpedance.di

import com.example.bioimpedance.viewmodel.BioImpedanceViewModel
import com.example.bioimpedance.viewmodel.BioImpedanceViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelBioImpedanceModule {
    @Binds
    @ViewModelScoped
    abstract fun bindBioImpedanceViewModel(bioImpedanceViewModelImpl: BioImpedanceViewModelImpl): BioImpedanceViewModel

}