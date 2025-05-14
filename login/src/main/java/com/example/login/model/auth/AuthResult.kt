package com.example.login.model.auth


/**
 * Representa o resultado de uma operação de autenticação bem-sucedida.
 *
 * @property userId O UID (User ID) do usuário ou empresa autenticado, fornecido pelo Firebase.
 * @property loginType O tipo de entidade que foi autenticada.
 */
data class AuthResult(
    val userId: String?, // UID do usuário ou empresa autenticado
    val loginType: LoginType // Tipo de usuário que logou (útil para redirecionamento)
)
