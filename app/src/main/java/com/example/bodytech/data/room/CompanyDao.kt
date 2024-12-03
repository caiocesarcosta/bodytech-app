package com.example.bodytech.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bodytech.model.company.CompanyEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CompanyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(company: CompanyEntity)

    @Query("SELECT * FROM companies WHERE companyId = :companyId")
    fun getById(companyId: String): Flow<CompanyEntity?>

    @Query("SELECT * FROM companies")
    fun getAll(): Flow<List<CompanyEntity>>

    @Update
    suspend fun update(company: CompanyEntity)

    @Delete
    suspend fun delete(company: CompanyEntity)
}