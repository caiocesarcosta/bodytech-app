package com.example.login.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.model.auth.LoginCredentials
import com.example.login.model.auth.LoginRequest
import com.example.login.model.auth.LoginType
import com.example.login.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para a tela de login. Gerencia o estado da UI e interage com o repositório de autenticação.
 * Utiliza um sealed class [LoginUiState] para representar o estado da UI, exposto via [StateFlow].
 *
 * @param authRepository O repositório responsável pela lógica de autenticação, injetado via Hilt.
 */
@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(), LoginViewModel {

    // Estado da UI, usando MutableStateFlow com um valor inicial (Idle)
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    // Expõe o estado como StateFlow imutável para a camada de apresentação
    override val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Variáveis para armazenar temporariamente o e-mail, senha e tipo de login selecionado
    private var currentEmail: String = ""
    private var currentPassword: String = ""
    private var currentLoginType: LoginType = LoginType.USER // Valor padrão inicial

    /**
     * Atualiza o e-mail digitado pelo usuário.
     * @param email O novo valor do e-mail.
     */
    override fun onEmailChanged(email: String) {
        currentEmail = email
        // Ao digitar, podemos retornar para o estado Idle se o estado atual for Failure
        if (_uiState.value is LoginUiState.Failure) {
            _uiState.value = LoginUiState.Idle
        }
    }

    /**
     * Atualiza a senha digitada pelo usuário.
     * @param password O novo valor da senha.
     */
    override fun onPasswordChanged(password: String) {
        currentPassword = password
        // Ao digitar, podemos retornar para o estado Idle se o estado atual for Failure
        if (_uiState.value is LoginUiState.Failure) {
            _uiState.value = LoginUiState.Idle
        }
    }

    /**
     * Atualiza o tipo de login selecionado pelo usuário (Usuário ou Empresa).
     * @param loginType O tipo de login selecionado.
     */
    override fun onLoginTypeSelected(loginType: LoginType) {
        currentLoginType = loginType
        // Ao mudar o tipo, podemos retornar para o estado Idle se não estivermos já nele
        if (_uiState.value != LoginUiState.Idle) {
            _uiState.value = LoginUiState.Idle
        }
    }

    /**
     * Inicia o processo de login quando o botão é clicado.
     * Lança uma coroutine para realizar a operação assíncrona.
     */
    override fun onLoginClick() {
        // 1. Validar inputs
        if (currentEmail.isBlank() || currentPassword.isBlank()) {
            // Atualiza o estado para Failure com a mensagem de erro de validação
            _uiState.value = LoginUiState.Failure(Exception("Por favor, preencha e-mail e senha."))
            return // Sai da função
        }

        // Evita múltiplas tentativas de login se já estiver carregando
        if (_uiState.value is LoginUiState.Loading) {
            return
        }

        // 2. Indica estado de carregamento
        _uiState.value = LoginUiState.Loading

        // 3. Lança uma coroutine para realizar a operação de login
        viewModelScope.launch {
            val credentials = LoginCredentials(currentEmail, currentPassword)
            val loginRequest = LoginRequest(credentials, currentLoginType)

            val result = authRepository.login(loginRequest)

            // 4. Processa o resultado da operação de login
            _uiState.value = if(result.isSuccess){
                // Login bem-sucedido, atualiza para o estado Success
                // Opcional: Se LoginUiState.Success precisar do tipo de login, passe aqui:
                // LoginUiState.Success(currentLoginType)
                LoginUiState.Success(currentLoginType) // Usando o objeto Success simples por enquanto
            } else {
                // Login falhou, atualiza para o estado Failure com a exceção
                LoginUiState.Failure(result.exceptionOrNull() ?: Exception("Erro desconhecido no login."))
            }
        }
    }

    /**
     * Chamado pela UI para indicar que o erro [LoginUiState.Failure] foi exibido.
     * Retorna o estado para [LoginUiState.Idle] após o erro ser mostrado na UI.
     */
    override fun onErrorShown() {
        // Só limpa o erro se o estado atual for Failure
        if (_uiState.value is LoginUiState.Failure) {
            _uiState.value = LoginUiState.Idle
        }
    }

    /**
     * Chamado pela UI após a navegação para a próxima tela em [LoginUiState.Success].
     * Retorna o estado para [LoginUiState.Idle] após o login bem-sucedido ser tratado na UI.
     */
    override fun onLoginSuccessHandled() {
        // Só limpa o estado de sucesso se o estado atual for Success
        if (_uiState.value is LoginUiState.Success) {
            _uiState.value = LoginUiState.Idle
        }
    }
}