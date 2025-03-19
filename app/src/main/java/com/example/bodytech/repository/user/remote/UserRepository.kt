package com.example.bodytech.repository.user.remote

interface UserRepository {
    suspend fun createAllUsersFromJson(): Result<Unit>

}