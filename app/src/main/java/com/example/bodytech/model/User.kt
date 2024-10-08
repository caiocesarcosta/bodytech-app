package com.example.bodytech.model

data class User(
    val userId: String? = null,
    val password: String? = null,
    val name: String? = null,
    val email: String? = null,
    val birthDate: String? = null,
    val gender: String? = null,
    val companies: List<String>? = null
)

data class BioimpedanceData(
    val date: String? = null,
    val companyId: String? = null,
    val weight: Double? = null,
    val height: Double? = null,
    val bmi: Double? = null
    // ... outras métricas relevantes
)
