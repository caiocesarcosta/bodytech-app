package com.example.bodytech.model.company

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companies")
data class Company(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "companyId")
    val companyId: Int? = null,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "address")
    val address: String?,
    @ColumnInfo(name = "contact")
    val contact: String?,

    @ColumnInfo(name = "services")

    val services: List<String>?
    // ... outros campos relevantes para a empresa
)