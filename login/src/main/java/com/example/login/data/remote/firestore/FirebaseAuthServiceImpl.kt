package com.example.login.data.remote.firestore

import com.example.login.data.remote.AuthenticationService
import com.example.login.model.auth.AuthResult
import com.example.login.model.auth.LoginType
import com.example.login.model.auth.LoginCredentials
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementação da interface [AuthenticationService] utilizando o Firebase Authentication e Firestore.
 * Gerencia a autenticação com email/senha e verifica o tipo de entidade no Firestore.
 *
 * @param firebaseAuth Instância do Firebase Authentication fornecida via DI.
 * @param firestore Instância do Firebase Firestore fornecida via DI.
 */
class FirebaseAuthServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthenticationService {

    // Caminhos das coleções no Firestore, documentados para clareza
    private val usersCollection = firestore.collection("users")
    private val companiesCollection = firestore.collection("companies")

    /**
     * @see AuthenticationService.loginUser
     */
    override suspend fun loginUser(credentials: LoginCredentials): Result<AuthResult> {
        return try {
            // Tenta autenticar com Firebase Auth
            val authResult = firebaseAuth.signInWithEmailAndPassword(
                credentials.email,
                credentials.password
            ).await()

            val firebaseUser = authResult.user
            if (firebaseUser != null) {
                // Autenticação bem-sucedida, agora verifica se este UID corresponde a um USUÁRIO no Firestore
                val userDocument = usersCollection.document(firebaseUser.uid).get().await()

                if (userDocument.exists()) {
                    // Encontrou o documento do usuário, o login como USUÁRIO é válido
                    Result.success(AuthResult(firebaseUser.uid, LoginType.USER))
                } else {
                    // Autenticou no Firebase, mas não encontrou o documento correspondente na coleção 'users'.
                    // Assume que as credenciais não correspondem ao tipo de login selecionado.
                    firebaseAuth.signOut() // Opcional: desloga para evitar estado inconsistente
                    Result.failure(Exception("Credenciais não correspondem a um usuário."))
                }
            } else {
                // Autenticação no Firebase Auth falhou
                Result.failure(Exception("Falha na autenticação do Firebase."))
            }
        } catch (e: Exception) {
            // Captura exceções durante o processo de login (Firebase Auth, Firestore, etc.)
            Result.failure(e)
        }
    }

    /**
     * @see AuthenticationService.loginCompany
     */
    override suspend fun loginCompany(credentials: LoginCredentials): Result<AuthResult> {
        return try {
            // Tenta autenticar com Firebase Auth (o mesmo método para usuário e empresa)
            val authResult = firebaseAuth.signInWithEmailAndPassword(
                credentials.email,
                credentials.password
            ).await()

            val firebaseUser = authResult.user
            if (firebaseUser != null) {
                // Autenticação bem-sucedida, agora verifica se este UID corresponde a uma EMPRESA no Firestore
                val companyDocument = companiesCollection.document(firebaseUser.uid).get().await()

                if (companyDocument.exists()) {
                    // Encontrou o documento da empresa, o login como EMPRESA é válido
                    Result.success(AuthResult(firebaseUser.uid, LoginType.COMPANY))
                } else {
                    // Autenticou no Firebase, mas não encontrou o documento correspondente na coleção 'companies'.
                    // Assume que as credenciais não correspondem ao tipo de login selecionado.
                    firebaseAuth.signOut() // Opcional: desloga
                    Result.failure(Exception("Credenciais não correspondem a uma empresa."))
                }
            } else {
                // Autenticação no Firebase Auth falhou
                Result.failure(Exception("Falha na autenticação do Firebase."))
            }
        } catch (e: Exception) {
            // Captura exceções durante o processo de login
            Result.failure(e)
        }
    }
}