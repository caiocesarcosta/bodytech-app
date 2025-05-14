package com.example.login.di

import com.example.login.data.remote.AuthenticationService
import com.example.login.data.remote.firestore.FirebaseAuthServiceImpl
import com.example.login.repository.auth.AuthRepository
import com.example.login.repository.auth.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt que fornece as dependências relacionadas à autenticação.
 * Inclui bindings para as interfaces de repositório e serviço.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationModule {

    /**
     * Fornece uma única instância do [AuthRepository].
     *
     * @param authRepositoryImpl A implementação concreta do repositório.
     * @return A instância de [AuthRepository].
     */
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    /**
     * Fornece uma única instância do [AuthenticationService].
     *
     * @param firebaseAuthServiceImpl A implementação concreta do serviço utilizando Firebase.
     * @return A instância de [AuthenticationService].
     */
    @Binds
    @Singleton
    abstract fun bindAuthenticationService(
        firebaseAuthServiceImpl: FirebaseAuthServiceImpl
    ): AuthenticationService
}