package com.example.bioimpedance.repository

import android.content.Context
import com.example.bioimpedance.model.BioImpedanceData
import com.example.bioimpedance.model.BioimpedanceDataEntity
import kotlinx.coroutines.flow.Flow


interface BioImpedanceRepository {
    suspend fun saveBioImpedanceData(data: BioImpedanceData): Result<Unit>
    suspend fun getCompanyIdForCurrentUser(): String?

    suspend fun createAllBioImpedanceDataFromJson(): Result<Unit>

//    suspend fun createAllBioimpedanceDataFromJson(context: Context): Result<Unit>
//    suspend fun getBioimpedanceData(userId: String, companyId: String): Flow<List<BioimpedanceDataEntity>>
//    suspend fun updateBioimpedanceData(data: BioimpedanceDataEntity): Result<Unit>
//    suspend fun deleteBioimpedanceData(data: BioimpedanceDataEntity): Result<Unit>

}
