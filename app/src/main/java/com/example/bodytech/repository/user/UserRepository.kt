package com.example.bodytech.repository.user

interface UserRepository {
    suspend fun createAllUsersFromJson(): Result<Unit>

}