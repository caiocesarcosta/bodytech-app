package com.example.bioimpedance.model

data class BioImpedanceData(
    val userId: String? = null,
    val bioimpedanceId: String? = null,
    val date: String? = null,
    val companyId: String,
    val weight: Double? = null,
    val height: Double? = null,
    val bmi: Double? = null,
    val bodyFat: Double? = null,
    val muscleMass: Double? = null
)