package com.example.bodytech.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Hilt que fornece instâncias das classes do Firebase SDK.
 * Essas classes não podem ser injetadas diretamente, então usamos @Provides.
 */
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    /**
     * Fornece uma única instância do [FirebaseAuth].
     *
     * @return A instância de [FirebaseAuth].
     */
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    /**
     * Fornece uma única instância do [FirebaseFirestore].
     *
     * @return A instância de [FirebaseFirestore].
     */
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}