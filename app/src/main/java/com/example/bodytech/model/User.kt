package com.example.bodytech.model

data class User(
    val userId: String? = null,
    val name: String? = null,
    val email: String? = null,
    val birthDate: String? = null,
    val gender: String? = null,
    val companies: List<String>? = null,
    var password: String? = null // Adicione o campo password
)
