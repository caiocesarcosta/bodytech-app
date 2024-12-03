package com.example.bodytech.model.company

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companies")
data class CompanyEntity(
    @PrimaryKey val companyId: String? = null,
    val name: String? = null,
    val address: String? = null,
    val contact: String? = null,
    val services: List<String>? = null
    // ... outros campos relevantes para a empresa
)