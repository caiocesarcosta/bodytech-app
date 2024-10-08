package com.example.bodytech.viewmodel.user

import androidx.lifecycle.LiveData

interface UserViewModel {
    fun createAllUsersFromJson()
    val createUsersStatus: LiveData<Result<Unit>>

}