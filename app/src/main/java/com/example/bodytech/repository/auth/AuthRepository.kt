package com.example.bodytech.repository.auth

import com.example.bodytech.model.user.User // Reutiliza o modelo User
import com.google.firebase.auth.AuthResult // Para resultados de autenticação

/**
 * Interface para as operações de repositório relacionadas à autenticação.
 * Define métodos para registro, login e logout de usuários.
 */
interface AuthRepository {
    /**
     * Registra um novo usuário com e-mail e senha.
     * Após o registro bem-sucedido no Firebase Authentication,
     * um documento de usuário básico é criado no Firestore.
     *
     * @param email O e-mail do usuário para registro.
     * @param password A senha do usuário para registro.
     * @return [Result.success] com o [AuthResult] se o registro for bem-sucedido,
     * [Result.failure] com uma exceção em caso de falha.
     */
    suspend fun registerUser(email: String, password: String): Result<AuthResult>

    /**
     * Faz o login de um usuário existente com e-mail e senha.
     *
     * @param email O e-mail do usuário para login.
     * @param password A senha do usuário para login.
     * @return [Result.success] com o [AuthResult] se o login for bem-sucedido,
     * [Result.failure] com uma exceção em caso de falha.
     */
    suspend fun loginUser(email: String, password: String): Result<AuthResult>

    /**
     * Realiza o logout do usuário atualmente autenticado.
     */
    fun logoutUser()

    /**
     * Verifica se há um usuário logado atualmente.
     * @return true se houver um usuário logado, false caso contrário.
     */
    fun isUserLoggedIn(): Boolean

    /**
     * Obtém o ID do usuário atualmente logado.
     * @return O UID do usuário logado, ou null se nenhum usuário estiver logado.
     */
    fun getCurrentUserId(): String?
}