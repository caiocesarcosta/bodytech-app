package com.example.bodytech.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodytech.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Implementação concreta de [AuthViewModel].
 * Gerencia o estado da UI para operações de autenticação (registro, login, logout).
 *
 * @param authRepository O repositório de autenticação para interagir com o Firebase.
 */
@HiltViewModel
class AuthViewModelImp @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(), AuthViewModel {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    override val authState: StateFlow<AuthState> = _authState

    /**
     * Tenta registrar um novo usuário.
     * Atualiza [authState] com [AuthState.Loading], [AuthState.Success] ou [AuthState.Failure].
     * @param email O e-mail do usuário.
     * @param password A senha do usuário.
     */
    override fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.registerUser(email, password)
            _authState.value = if (result.isSuccess) {
                AuthState.Success
            } else {
                AuthState.Failure(result.exceptionOrNull()?.message ?: "Erro desconhecido ao registrar.")
            }
        }
    }

    /**
     * Tenta fazer login de um usuário existente.
     * Atualiza [authState] com [AuthState.Loading], [AuthState.Success] ou [AuthState.Failure].
     * @param email O e-mail do usuário.
     * @param password A senha do usuário.
     */
    override fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.loginUser(email, password)
            _authState.value = if (result.isSuccess) {
                AuthState.Success
            } else {
                AuthState.Failure(result.exceptionOrNull()?.message ?: "Erro desconhecido ao fazer login.")
            }
        }
    }

    /**
     * Realiza o logout do usuário.
     * Atualiza [authState] para [AuthState.Idle] após o logout.
     */
    override fun logout() {
        authRepository.logoutUser()
        _authState.value = AuthState.Idle // Resetar o estado da UI após logout
    }

    /**
     * Verifica se o usuário está logado.
     * @return true se o usuário estiver logado, false caso contrário.
     */
    override fun isUserLoggedIn(): Boolean {
        return authRepository.isUserLoggedIn()
    }

    /**
     * Reseta o [authState] para [AuthState.Idle].
     * Útil para limpar mensagens de estado da UI.
     */
    override fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
}