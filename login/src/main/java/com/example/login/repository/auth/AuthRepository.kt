package com.example.login.repository.auth

import com.example.login.model.auth.AuthResult
import com.example.login.model.auth.LoginRequest

/**
 * Interface que define as operações do repositório de autenticação.
 * Atua como uma camada de abstração entre a ViewModel e as fontes de dados de autenticação.
 */
interface AuthRepository {

    /**
     * Realiza uma tentativa de login com base nas informações da requisição [LoginRequest].
     * Delega a lógica específica de autenticação para o serviço apropriado (e.g., Firebase).
     *
     * @param loginRequest O objeto contendo as credenciais e o tipo de entidade que está tentando logar.
     * @return Um [Result] contendo [AuthResult] em caso de sucesso, ou uma [Exception] em caso de falha.
     */
    suspend fun login(loginRequest: LoginRequest): Result<AuthResult>

    // Você pode adicionar aqui outros métodos relacionados à autenticação
    // (cadastro de usuário/empresa, resetar senha, etc.) e documentá-los com KDoc.
}