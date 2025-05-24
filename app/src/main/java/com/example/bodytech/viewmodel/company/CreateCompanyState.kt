package com.example.bodytech.viewmodel.company

// A sealed class CreateCompanyState permanece a mesma
sealed class CreateCompanyState {
    object Idle : CreateCompanyState()
    object Loading : CreateCompanyState()
    object Success : CreateCompanyState()
    data class Failure(val exception: Throwable?) : CreateCompanyState()
}
