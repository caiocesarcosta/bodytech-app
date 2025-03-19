package com.example.bioimpedance.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "bioimpedance_data")
data class BioimpedanceDataEntity(
    @PrimaryKey
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


//@ColumnInfo(name = "body_fat") val bodyFat: Double,
//@ColumnInfo(name = "muscle_mass ") val muscleMass: Double