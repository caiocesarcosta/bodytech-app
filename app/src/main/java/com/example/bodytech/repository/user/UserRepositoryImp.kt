package com.example.bodytech.repository.user

import android.content.Context
import android.util.Log
import com.example.bodytech.model.User
import com.google.common.reflect.TypeToken
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) : UserRepository {

    private val db = Firebase.firestore

    override suspend fun createAllUsersFromJson(): Result<Unit> {
        return withContext(ioDispatcher) {  // Executa a operação no dispatcher de I/O
            try {
                // Carrega os dados do arquivo JSON
                val jsonFileString = context.assets.open("users.json").bufferedReader().use { it.readText() }
                val userType = object : TypeToken<List<User>>() {}.type
                val users = Gson().fromJson<List<User>>(jsonFileString, userType)

                // Itera pelos usuários e cria os documentos no Firestore
                for (user in users) {
                    db.collection("users")
                        .add(user)
                        .await()
                    Log.d("FirestoreUserRepository", "Usuário ${user.userId} criado com sucesso!")
                }

                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("FirestoreUserRepository", "Erro ao criar usuários", e)
                Result.failure(e)
            }
        }
    }
}