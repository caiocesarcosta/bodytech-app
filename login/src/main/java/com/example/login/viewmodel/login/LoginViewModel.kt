package com.example.login.viewmodel.login

import com.example.login.model.auth.LoginType
import kotlinx.coroutines.flow.StateFlow

interface LoginViewModel {
    // Estado da UI, exposto como StateFlow para a camada de apresentação (Compose)
    val uiState: StateFlow<LoginUiState>

    /**
     * Atualiza o e-mail digitado pelo usuário.
     * @param email O novo valor do e-mail.
     */
    fun onEmailChanged(email: String)

    /**
     * Atualiza a senha digitada pelo usuário.
     * @param password O novo valor da senha.
     */
    fun onPasswordChanged(password: String)

    /**
     * Atualiza o tipo de login selecionado pelo usuário (Usuário ou Empresa).
     * @param loginType O tipo de login selecionado.
     */
    fun onLoginTypeSelected(loginType: LoginType)

    /**
     * Inicia o processo de login quando o botão é clicado.
     * Lança uma coroutine para realizar a operação assíncrona.
     */
    fun onLoginClick()

    /**
     * Chamado para indicar que o erro foi exibido e pode ser limpo.
     */
    fun onErrorShown()

    /**
     * Chamado após a navegação bem-sucedida para resetar o estado de sucesso.
     */
    fun onLoginSuccessHandled()
}