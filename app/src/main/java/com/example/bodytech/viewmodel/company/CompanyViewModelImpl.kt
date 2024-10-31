package com.example.bodytech.viewmodel.company

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodytech.repository.company.CompanyRepository
import com.example.bodytech.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyViewModelImpl @Inject constructor(
    private val companyRepository: CompanyRepository
) : ViewModel(), CompanyViewModel {

    private val _createCompaniesStatus = MutableLiveData<Result<Unit>>()
    override val createCompaniesStatus: LiveData<Result<Unit>> = _createCompaniesStatus

    override fun createAllCompaniesFromJson() {
        viewModelScope.launch {
            _createCompaniesStatus.value = companyRepository.createAllCompaniesFromJson()
        }
    }
}