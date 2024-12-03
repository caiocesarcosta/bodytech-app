package com.example.bioimpedance.data.room


import androidx.room.*
import com.example.bioimpedance.model.BioimpedanceDataEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface BioimpedanceDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bioimpedanceData: BioimpedanceDataEntity)

    @Query("SELECT * FROM bioimpedance_data WHERE userId = :userId AND companyId = :companyId")
    fun getAllByUserIdAndCompanyId(userId: String, companyId: String): Flow<List<BioimpedanceDataEntity>>

    @Query("SELECT * FROM bioimpedance_data WHERE bioimpedanceId = :bioimpedanceId")
    suspend fun getById(bioimpedanceId: UUID): BioimpedanceDataEntity?

    @Update
    suspend fun update(bioimpedanceData: BioimpedanceDataEntity)

    @Delete
    suspend fun delete(bioimpedanceData: BioimpedanceDataEntity)
}