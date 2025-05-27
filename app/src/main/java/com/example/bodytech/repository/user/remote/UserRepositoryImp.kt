package com.example.bodytech.repository.user.remote

import android.content.Context
import android.util.Log
import com.example.bodytech.model.user.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação de [UserRepository] para interação com o Firestore.
 * Gerencia operações de CRUD para documentos da coleção 'users'.
 * Utiliza Gson para carregar dados de usuários a partir de um arquivo JSON.
 *
 * @param context O contexto da aplicação, injetado por Hilt.
 * @param ioDispatcher O CoroutineDispatcher para operações de I/O.
 */
class UserRepositoryImp @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {

    private val db = Firebase.firestore
    private val usersCollection = db.collection("users") // Referência à coleção de usuários

    /**
     * Cria todos os usuários a partir do arquivo 'users.json' nos assets.
     * Cada usuário é adicionado como um documento no Firestore, usando seu 'id' como Document ID.
     * Se um usuário com o mesmo ID já existir, ele será sobrescrito.
     */
    override suspend fun createAllUsersFromJson(): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val jsonFileString = context.assets.open("users.json").bufferedReader().use { it.readText() }
                val userType = object : TypeToken<List<User>>() {}.type
                val users = Gson().fromJson<List<User>>(jsonFileString, userType)

                for (user in users) {
                    usersCollection.document(user.id) // Usa o 'id' do usuário como ID do documento
                        .set(user) // .set() sobrescreve se existir, cria se não
                        .await()
                    Log.d("UserRepositoryImp", "Usuário ${user.id} criado/atualizado com sucesso!")
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("UserRepositoryImp", "Erro ao criar usuários a partir do JSON", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Cria um novo documento de usuário no Firestore.
     * @param user O objeto [User] a ser criado. O 'id' do usuário será usado como ID do documento.
     * @return [Result.success] com o usuário criado se a operação for bem-sucedida, [Result.failure] caso contrário.
     */
    override suspend fun createUser(user: User): Result<User> {
        return withContext(ioDispatcher) {
            try {
                usersCollection.document(user.id)
                    .set(user)
                    .await()
                Result.success(user)
            } catch (e: Exception) {
                Log.e("UserRepositoryImp", "Erro ao criar usuário ${user.id}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Obtém um documento de usuário pelo seu ID.
     * @param userId O ID do usuário a ser buscado.
     * @return [Result.success] com o objeto [User] se encontrado, [Result.failure] se não encontrado ou ocorrer um erro.
     */
    override suspend fun getUser(userId: String): Result<User> {
        return withContext(ioDispatcher) {
            try {
                val documentSnapshot = usersCollection.document(userId).get().await()
                val user = documentSnapshot.toObject(User::class.java)
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(NoSuchElementException("Usuário com ID $userId não encontrado."))
                }
            } catch (e: Exception) {
                Log.e("UserRepositoryImp", "Erro ao obter usuário $userId", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Atualiza um documento de usuário existente no Firestore.
     * @param userId O ID do usuário a ser atualizado.
     * @param user O objeto [User] com os dados atualizados. O 'id' deve corresponder ao userId.
     * @return [Result.success] com o usuário atualizado se a operação for bem-sucedida, [Result.failure] caso contrário.
     */
    override suspend fun updateUser(userId: String, user: User): Result<User> {
        return withContext(ioDispatcher) {
            try {
                // Garante que o ID do objeto corresponde ao ID do documento
                if (user.id != userId) {
                    return@withContext Result.failure(IllegalArgumentException("O ID do usuário no objeto não corresponde ao ID do documento fornecido."))
                }
                usersCollection.document(userId)
                    .set(user) // .set() com um objeto completo sobrescreve o documento
                    .await()
                Result.success(user)
            } catch (e: Exception) {
                Log.e("UserRepositoryImp", "Erro ao atualizar usuário $userId", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Deleta um documento de usuário do Firestore.
     * @param userId O ID do usuário a ser deletado.
     * @return [Result.success] se o usuário for deletado com sucesso, [Result.failure] caso contrário.
     */
    override suspend fun deleteUser(userId: String): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                usersCollection.document(userId)
                    .delete()
                    .await()
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("UserRepositoryImp", "Erro ao deletar usuário $userId", e)
                Result.failure(e)
            }
        }
    }
}