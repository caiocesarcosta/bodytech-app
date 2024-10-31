package com.example.bioimpedance.repository

import android.content.Context
import com.example.bioimpedance.model.BioImpedanceData


interface BioImpedanceRepository {
    suspend fun saveBioImpedanceData(data: BioImpedanceData): Result<Unit>
    suspend fun createAllBioImpedanceDataFromJson(): Result<Unit>
    suspend fun getCompanyIdForCurrentUser(): String?
}
