package com.example.bodytech.repository.user

import android.content.Context

interface UserRepository {
    suspend fun createAllUsersFromJson(context: Context): Result<Unit>
}