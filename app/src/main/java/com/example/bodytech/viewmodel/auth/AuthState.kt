package com.example.bodytech.viewmodel.auth

/**
 * [AuthState] representa os diferentes estados da UI durante as operações de autenticação.
 */
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Failure(val message: String) : AuthState()
}