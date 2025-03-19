package com.example.bodytech.data.local.room.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.bodytech.model.company.Company
import com.example.bodytech.model.user.UserCompanyCrossRefEntity

@Dao
interface UserCompanyDao {
    @Insert
    suspend fun addCompanyToUser(userCompanyCrossRef: UserCompanyCrossRefEntity)

    @Transaction
    @Query("SELECT c.* FROM companies c INNER JOIN user_company_cross_ref uc ON c.companyId = uc.companyId WHERE uc.userId = :userId")
    suspend fun getCompaniesForUser(userId: String): List<Company>

    @Query("DELETE FROM user_company_cross_ref WHERE userId = :userId AND companyId = :companyId")
    suspend fun removeCompanyFromUser(userId: String, companyId: String)

    @Query("UPDATE user_company_cross_ref SET hasPermission = :hasPermission WHERE userId = :userId AND companyId = :companyId")
    suspend fun updateUserCompanyPermission(userId: String, companyId: String, hasPermission: Boolean)

    @Query("SELECT * FROM user_company_cross_ref WHERE userId = :userId AND companyId = :companyId")
    suspend fun getUserCompanyRelation(userId: String, companyId: String): UserCompanyCrossRefEntity?
}