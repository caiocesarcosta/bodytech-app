package com.example.bioimpedance.data.local.room

import androidx.room.*
import com.example.bioimpedance.model.BioimpedanceDataEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface BioimpedanceDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bioimpedanceDataEntity: BioimpedanceDataEntity) // Retorna Long (ID da linha inserida)

    @Query("SELECT * FROM bioimpedance_data WHERE userId = :userId AND companyId = :companyId")
    fun getAllByUserIdAndCompanyId(userId: String, companyId: String): Flow<List<BioimpedanceDataEntity>>

    @Query("SELECT * FROM bioimpedance_data WHERE bioimpedanceId = :bioimpedanceId")
    fun getById(bioimpedanceId: String): BioimpedanceDataEntity // Retorna BioimpedanceDataEntity?

    @Update
    fun update(bioimpedanceData: BioimpedanceDataEntity): Int // Retorna Int (número de linhas atualizadas)

    @Delete
    fun delete(bioimpedanceData: BioimpedanceDataEntity): Int // Retorna Int (número de linhas excluídas)
}