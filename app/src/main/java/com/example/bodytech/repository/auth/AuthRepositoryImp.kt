package com.example.bodytech.repository.auth

import android.util.Log
import com.example.bodytech.model.user.User
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton // Usar Singleton para o repositório de autenticação

/**
 * Implementação concreta de [AuthRepository] que interage com Firebase Authentication
 * e Firebase Firestore para gerenciar usuários.
 *
 * @param auth A instância de FirebaseAuth injetada.
 * @param db A instância de FirebaseFirestore injetada.
 * @param ioDispatcher O CoroutineDispatcher para operações de I/O.
 */
@Singleton // Marcado como Singleton para garantir uma única instância
class AuthRepositoryImp @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {

    private val usersCollection = db.collection("users")

    /**
     * Implementação para registrar um novo usuário no Firebase Authentication.
     * Em caso de sucesso, também cria um documento de usuário no Firestore.
     */
    override suspend fun registerUser(email: String, password: String): Result<AuthResult> {
        return withContext(ioDispatcher) {
            try {
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = authResult.user

                if (firebaseUser != null) {
                    // Crie um documento de usuário básico no Firestore
                    // O ID do documento será o UID do Firebase Auth.
                    val newUser = User(
                        id = firebaseUser.uid,
                        name = null, // Nome pode ser adicionado depois
                        email = firebaseUser.email,
                        birthDate = null,
                        gender = null,
                        associatedCompanies = null
                    )
                    usersCollection.document(firebaseUser.uid)
                        .set(newUser) // set() para usar o UID como ID do documento
                        .await()
                    Log.d("AuthRepository", "Usuário Firestore criado para UID: ${firebaseUser.uid}")
                }
                Result.success(authResult)
            } catch (e: Exception) {
                Log.e("AuthRepository", "Erro no registro de usuário", e)
                val errorMessage = when (e) {
                    is FirebaseAuthWeakPasswordException -> "Senha muito fraca. Escolha uma senha mais forte."
                    is FirebaseAuthInvalidCredentialsException -> "Formato de e-mail inválido."
                    is FirebaseAuthUserCollisionException -> "Este e-mail já está em uso."
                    else -> "Erro desconhecido ao registrar: ${e.message}"
                }
                Result.failure(Exception(errorMessage, e)) // Empacota a mensagem de erro específica
            }
        }
    }

    /**
     * Implementação para fazer login de um usuário existente.
     */
    override suspend fun loginUser(email: String, password: String): Result<AuthResult> {
        return withContext(ioDispatcher) {
            try {
                val authResult = auth.signInWithEmailAndPassword(email, password).await()
                Result.success(authResult)
            } catch (e: Exception) {
                Log.e("AuthRepository", "Erro no login de usuário", e)
                val errorMessage = when (e) {
                    is FirebaseAuthInvalidCredentialsException -> "E-mail ou senha inválidos."
                    else -> "Erro desconhecido ao fazer login: ${e.message}"
                }
                Result.failure(Exception(errorMessage, e))
            }
        }
    }

    /**
     * Realiza o logout do usuário atualmente autenticado.
     */
    override fun logoutUser() {
        auth.signOut()
        Log.d("AuthRepository", "Usuário deslogado.")
    }

    /**
     * Verifica se há um usuário logado atualmente.
     * @return true se FirebaseAuth.currentUser não for nulo, false caso contrário.
     */
    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    /**
     * Obtém o UID do usuário atualmente logado.
     * @return O UID do usuário logado, ou null se nenhum usuário estiver logado.
     */
    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}