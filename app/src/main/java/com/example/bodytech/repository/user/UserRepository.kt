package com.example.bodytech.repository.user

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.bodytech.viewmodel.user.CreateUsersState

interface UserRepository {
    suspend fun createAllUsersFromJson(): Result<Unit>

}