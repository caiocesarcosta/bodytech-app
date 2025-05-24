package com.example.bodytech.viewmodel.company

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodytech.repository.company.remote.CompanyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow // Importar MutableStateFlow
import kotlinx.coroutines.flow.StateFlow    // Importar StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Implementação concreta de [CompanyViewModel].
 * Responsável por gerenciar o estado da UI relacionado à criação de empresas
 * e interagir com o [CompanyRepository].
 *
 * @param companyRepository O repositório para operações de dados de empresas.
 */
@HiltViewModel
class CompanyViewModelImpl @Inject constructor(
    private val companyRepository: CompanyRepository
) : ViewModel(), CompanyViewModel {

    // Alterado de MutableLiveData para MutableStateFlow
    private val _createCompaniesStatus =
        MutableStateFlow<CreateCompanyState>(CreateCompanyState.Idle)
    override val createCompaniesStatus: StateFlow<CreateCompanyState> = _createCompaniesStatus // Expondo como StateFlow

    /**
     * Inicia a operação de criação de todas as empresas a partir do JSON.
     * Atualiza o [createCompaniesStatus] durante o processo.
     */
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