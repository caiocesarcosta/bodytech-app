package com.example.login.viewmodel.login

import com.example.login.model.auth.LoginType


/**
 * Representa os possíveis estados da tela de login.
 * Selada para garantir que todos os estados são conhecidos e tratados.
 */
sealed class LoginUiState {

    /** Estado inicial ou quando não há operação em andamento e sem erro/sucesso visível. */
    data object Idle : LoginUiState()

    /** Estado indicando que uma operação de login está em andamento. */
    data object Loading : LoginUiState()

    /** Estado indicando que o login foi bem-sucedido.
     * Opcional: Pode conter o tipo de login se a UI precisar dessa informação.
     */
    // object Success : LoginUiState()
    data class Success(val loginType: LoginType) : LoginUiState() // Exemplo se precisar do tipo

    /** Estado indicando que ocorreu uma falha no login. Contém a exceção opcional. */
    data class Failure(val exception: Throwable?) : LoginUiState()
}

