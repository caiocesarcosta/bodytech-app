package com.example.bodytech.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val userId: String? = null,
    val name: String? = null,
    val email: String? = null,
    val birthDate: String? = null,
    val gender: String? = null,
    val companies: List<String>? = null,
    var password: String? = null // Adicione o campo password
)
