package com.example.bodytech.repository.auth

import com.example.bodytech.model.User
import com.example.bodytech.model.Company

interface AuthRepository {
    suspend fun signInAnonymously(): Result<Boolean>
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Boolean>
    suspend fun createUserWithEmailAndPassword(user: User): Result<Boolean>
    suspend fun signInCompanyWithEmailAndPassword(email: String, password: String): Result<Boolean>
    suspend fun createCompanyWithEmailAndPassword(company: Company): Result<Boolean>
    // ... (Outras funções de autenticação, se necessário)
}