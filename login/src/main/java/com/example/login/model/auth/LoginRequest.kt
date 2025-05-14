package com.example.login.model.auth

/**
 * Representa uma requisição de login, combinando as credenciais com o tipo de login.
 *
 * @property credentials As credenciais de login (e-mail e senha).
 * @property loginType O tipo de entidade que está tentando logar (Usuário ou Empresa).
 */
data class LoginRequest(
    val credentials: LoginCredentials,
    val loginType: LoginType
)
