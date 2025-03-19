package com.example.bodytech.data.remote.firestore

import com.example.bodytech.model.user.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserFirestoreApiImpl @Inject constructor(
    private val firestoreApi: UserFirestoreApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): UserFirestoreApi {

    override suspend fun createUser(user: User) = withContext(ioDispatcher) {
        firestoreApi.createUser(user)
    }

    override suspend fun getUser(userId: String) = withContext(ioDispatcher) {
        firestoreApi.getUser(userId)
    }

    override suspend fun updateUser(userId: String, user: User) = withContext(ioDispatcher) {
        firestoreApi.updateUser(userId, user)
    }

    override suspend fun deleteUser(userId: String) = withContext(ioDispatcher) {
        firestoreApi.deleteUser(userId)
    }
}