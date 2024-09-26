package com.example.bodytech.model

data class User(
    val userId: String? = null,
    val name: String? = null,
    val email: String? = null,
    val birthDate: String? = null,
    val gender: String? = null,
    val companies: List<String>? = null,
    val aestheticsData: AestheticsData? = null,
    val gymData: GymData? = null,
    val nutritionData: NutritionData? = null
)

data class AestheticsData(
    val companyId: String? = null,
    val shared: Boolean? = null,
    val bioimpedanceData: List<BioimpedanceData>? = null
)

data class GymData(
    val companyId: String? = null,
    val shared: Boolean? = null
    // ... outros campos relevantes para dados da academia
)

data class NutritionData(
    val companyId: String? = null,
    val shared: Boolean? = null
    // ... outros campos relevantes para dados de nutrição
)

data class BioimpedanceData(
    val date: String? = null,
    val companyId: String? = null,
    val weight: Double? = null,
    val height: Double? = null,
    val bmi: Double? = null
    // ... outras métricas relevantes
)
