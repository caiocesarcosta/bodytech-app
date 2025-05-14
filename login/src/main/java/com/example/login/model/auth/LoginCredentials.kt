package com.example.login.model.auth

/**
 * Representa as credenciais de login de um usuário ou empresa.
 *
 * @property email O endereço de e-mail utilizado para login.
 * @property password A senha associada ao e-mail.
 */
data class LoginCredentials(
    val email: String,
    val password: String
)