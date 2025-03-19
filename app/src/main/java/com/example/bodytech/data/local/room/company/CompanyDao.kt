package com.example.bodytech.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bodytech.model.company.Company
import kotlinx.coroutines.flow.Flow


@Dao
interface CompanyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(company: Company)

    @Query("SELECT * FROM companies WHERE companyId = :companyId")
    fun getById(companyId: String): Flow<Company?>

    @Query("SELECT * FROM companies")
    fun getAll(): Flow<List<Company>>

    @Update
    suspend fun update(company: Company)

    @Delete
    suspend fun delete(company: Company)
}