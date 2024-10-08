package com.example.bodytech.repository.user

import android.content.Context
import android.util.Log
import com.example.bodytech.model.User
import com.google.common.reflect.TypeToken
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImp @Inject constructor() : UserRepository {
    private val db = Firebase.firestore

    override suspend fun createAllUsersFromJson(context: Context): Result<Unit> {
        return try {
            // Carrega os dados do arquivo JSON
            val jsonFileString = context.assets.open("users.json").bufferedReader().use { it.readText() }
            val userType = object : TypeToken<List<User>>() {}.type
            val users = Gson().fromJson<List<User>>(jsonFileString, userType)

            // Itera pelos usuários e cria os documentos no Firestore
            for (user in users) {
                db.collection("users")
                    .add(user)
                    .await()
                Log.d("UserRepositoryImp", "Usuário ${user.userId} criado com sucesso!")
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("UserRepositoryImp", "Erro ao criar usuários", e)
            Result.failure(e)
        }
    }
}