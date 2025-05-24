package com.example.bioimpedance.model


import java.util.UUID

data class BioimpedanceDataEntity(
    val userId: String  = UUID.randomUUID().toString(),
    val bioimpedanceId: String,
    val companyId: String,
    val date: String?,
    val weight: Double,
    val height: Double,
    val bmi: Double,
    val bodyFat: Double,
    val muscleMass: Double
)


