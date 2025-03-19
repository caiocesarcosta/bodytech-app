package com.example.bodytech.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    val userId: Int? = null,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "email")
    val email: String?,
    @ColumnInfo(name = "birth_date")
    val birthDate: String?,
    @ColumnInfo(name = "gender")
    val gender: String?,
    @ColumnInfo(name = "password")
    var password: String? // Adicione o campo password
)
