package com.example.login.data.remote

import com.example.login.model.auth.AuthResult
import com.example.login.model.auth.LoginCredentials

/**
 * Interface que define as operações de autenticação que o aplicativo suporta.
 * Abstrai a fonte de dados de autenticação subjacente (Firebase, backend customizado, etc.).
 */
interface AuthenticationService {

    /**
     * Tenta realizar o login de um usuário comum com as credenciais fornecidas.
     *
     * @param credentials As credenciais de login (e-mail e senha).
     * @return Um Result contendo [AuthResult] em caso de sucesso, ou uma [Exception] em caso de falha.
     */
    suspend fun loginUser(credentials: LoginCredentials): Result<AuthResult>

    /**
     * Tenta realizar o login de uma empresa com as credenciais fornecidas.
     * Nota: Para login com email/senha, a implementação Firebase pode ser a mesma.
     * A diferenciação real do tipo de entidade (Usuário vs Empresa) ocorre após a autenticação inicial
     * e verificação no banco de dados (Firestore).
     *
     * @param credentials As credenciais de login (e-mail e senha).
     * @return Um Result contendo [AuthResult] em caso de sucesso, ou uma [Exception] em caso de falha.
     */
    suspend fun loginCompany(credentials: LoginCredentials): Result<AuthResult>

    // Você pode adicionar aqui métodos para cadastro, reset de senha, etc. futuramente,
    // e documentá-los com KDoc.
}