package com.example.bioimpedance.model

data class BioimpedanceData(
    val userId: String? = null,
    val companyId: String? = null,
    val date: String? = null,
    val weight: Double? = null,
    val height: Double? = null,
    val bmi: Double? = null
    // ... outras métricas
)
