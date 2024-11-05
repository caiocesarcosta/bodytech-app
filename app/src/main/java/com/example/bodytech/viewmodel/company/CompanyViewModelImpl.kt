package com.example.bodytech.viewmodel.company

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodytech.repository.company.CompanyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyViewModelImpl @Inject constructor(
    private val companyRepository: CompanyRepository
) : ViewModel(), CompanyViewModel {

    private val _createCompaniesStatus =
        MutableLiveData<CreateCompanyState>(CreateCompanyState.Idle)
    override val createCompaniesStatus: LiveData<CreateCompanyState> = _createCompaniesStatus

    override fun createAllCompaniesFromJson() {
        viewModelScope.launch {
            _createCompaniesStatus.value = CreateCompanyState.Loading

            val result = companyRepository.createAllCompaniesFromJson()

            _createCompaniesStatus.value = if (result.isSuccess) {
                CreateCompanyState.Success
            } else {
                CreateCompanyState.Failure(result.exceptionOrNull())
            }
        }
    }
}

sealed class CreateCompanyState {
    object Idle : CreateCompanyState()
    object Loading : CreateCompanyState()
    object Success : CreateCompanyState()
    data class Failure(val exception: Throwable?) : CreateCompanyState()
}