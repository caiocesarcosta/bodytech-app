package com.example.login.repository.auth

import com.example.login.data.remote.AuthenticationService
import com.example.login.model.auth.AuthResult
import com.example.login.model.auth.LoginRequest
import com.example.login.model.auth.LoginType
import javax.inject.Inject

/**
 * Implementação do [AuthRepository] que utiliza um [AuthenticationService] para realizar as operações de login.
 *
 * @param authenticationService O serviço de autenticação a ser utilizado (fornecido via DI).
 */
class AuthRepositoryImpl @Inject constructor(
    private val authenticationService: AuthenticationService
) : AuthRepository {

    /**
     * @see AuthRepository.login
     */
    override suspend fun login(loginRequest: LoginRequest): Result<AuthResult> {
        // Delega a chamada para o serviço de autenticação apropriado com base no tipo de login
        return when (loginRequest.loginType) {
            LoginType.USER -> authenticationService.loginUser(loginRequest.credentials)
            LoginType.COMPANY -> authenticationService.loginCompany(loginRequest.credentials)
        }
    }
}