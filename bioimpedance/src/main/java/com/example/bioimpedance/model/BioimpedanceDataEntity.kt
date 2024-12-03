package com.example.bioimpedance.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.util.UUID

@Entity(tableName = "bioimpedance_data")
data class BioimpedanceDataEntity(
    @PrimaryKey
    val bioimpedanceId: String = UUID.randomUUID().toString(),
    val userId: String,
    val companyId: String,
    val date: OffsetDateTime,
    val weight: Double,
    val height: Double,
    val bmi: Double,
    @ColumnInfo(name = "body_fat") val bodyFat: Double? = null,
    @ColumnInfo(name = "muscle_mass") val muscleMass: Double? = null
    // ... outras métricas de bioimpedância
)
