package com.example.bodytech.repository.auth

import android.util.Log
import com.example.bodytech.model.User
import com.example.bodytech.model.Company
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor() : AuthRepository {
    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    override suspend fun signInAnonymously(): Result<Boolean> {
        return try {
            auth.signInAnonymously().await()
            Log.d("AuthRepository", "Autenticação anônima bem-sucedida.")
            Result.success(true)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erro na autenticação anônima: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Log.d("AuthRepository", "Login do usuário bem-sucedido. Email: $email")
            Result.success(true)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erro no login do usuário: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun createUserWithEmailAndPassword(user: User): Result<Boolean> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(user.email!!, user.password!!).await()
            val userId = authResult.user?.uid ?: throw Exception("Erro ao obter o UID do usuário")

            // Salva os dados do usuário no Firestore
            val userWithoutPassword = user.copy(password = null) // Remove a senha para segurança
            db.collection("users").document(userId)
                .set(userWithoutPassword)
                .await()

            Log.d("AuthRepository", "Usuário criado com sucesso. UID: $userId")
            Result.success(true)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erro ao criar usuário: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun signInCompanyWithEmailAndPassword(email: String, password: String): Result<Boolean> {
        return try {
            // Simulando login da empresa (você precisará implementar a lógica real)
            Log.d("AuthRepository", "Login da empresa bem-sucedido. Email: $email")
            Result.success(true)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erro no login da empresa: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun createCompanyWithEmailAndPassword(company: Company): Result<Boolean> {
        return try {
            // Simulando criação da empresa (você precisará implementar a lógica real)
            // 1. Crie o usuário da empresa no Firebase Authentication (se necessário)
            // 2. Salve os dados da empresa no Firestore na coleção "companies"
            Log.d("AuthRepository", "Empresa criada com sucesso. Nome: ${company.name}")
            Result.success(true)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erro ao criar empresa: ${e.message}", e)
            Result.failure(e)
        }
    }
}