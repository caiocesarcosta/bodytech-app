package com.example.bioimpedance.repository

import com.example.bioimpedance.model.BioimpedanceData


interface BioimpedanceRepository {
    suspend fun saveBioimpedanceData(data: BioimpedanceData): Result<Unit>
    suspend fun getCompanyIdForCurrentUser(): String?
}