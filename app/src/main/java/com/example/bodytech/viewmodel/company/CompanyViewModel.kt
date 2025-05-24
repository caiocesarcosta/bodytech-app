package com.example.bodytech.viewmodel.company

import kotlinx.coroutines.flow.StateFlow // Importar StateFlow

/**
 * Interface para a ViewModel de [Company].
 * Define as operações e o estado de criação de empresas.
 */
interface CompanyViewModel {
    /**
     * Inicia a criação de todas as empresas a partir de um arquivo JSON.
     */
    fun createAllCompaniesFromJson()

    /**
     * O estado atual da operação de criação de empresas.
     * É um [StateFlow] que emite atualizações do status (Idle, Loading, Success, Failure).
     */
    val createCompaniesStatus: StateFlow<CreateCompanyState> // Alterado de LiveData para StateFlow
}