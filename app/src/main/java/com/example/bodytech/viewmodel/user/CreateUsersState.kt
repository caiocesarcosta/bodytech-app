package com.example.bodytech.viewmodel.user

// A sealed class CreateUsersState permanece a mesma
sealed class CreateUsersState {
    object Idle : CreateUsersState()
    object Loading : CreateUsersState()
    object Success : CreateUsersState()
    data class Failure(val exception: Throwable?) : CreateUsersState()
}
