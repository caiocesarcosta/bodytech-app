package com.example.bodytech.viewmodel.auth

import kotlinx.coroutines.flow.StateFlow

/**
 * Interface para a ViewModel de Autenticação.
 * Expõe operações de autenticação e o estado da UI.
 */
interface AuthViewModel {
    /**
     * O estado atual da operação de autenticação (registro/login).
     * Um [StateFlow] que emite atualizações (Idle, Loading, Success, Failure).
     */
    val authState: StateFlow<AuthState>

    /**
     * Inicia o processo de registro de um novo usuário.
     * @param email O e-mail do usuário.
     * @param password A senha do usuário.
     */
    fun register(email: String, password: String)

    /**
     * Inicia o processo de login de um usuário existente.
     * @param email O e-mail do usuário.
     * @param password A senha do usuário.
     */
    fun login(email: String, password: String)

    /**
     * Inicia o processo de logout do usuário atual.
     */
    fun logout()

    /**
     * Verifica se há um usuário logado atualmente.
     * @return true se o usuário estiver logado, false caso contrário.
     */
    fun isUserLoggedIn(): Boolean

    /**
     * Limpa o estado de autenticação para [AuthState.Idle].
     * Útil após um sucesso ou falha para resetar a UI.
     */
    fun resetAuthState()
}