package com.example.bodytech.data.local.room.company

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.bodytech.model.company.CompanyUsersCrossRefEntity
import com.example.bodytech.model.user.User

@Dao
interface CompanyUserDao {

    @Insert
    suspend fun addUserToCompany(companyUserCrossRefEntity: CompanyUsersCrossRefEntity)

    @Transaction
    @Query("SELECT u.* FROM users u INNER JOIN company_user_cross_ref uc ON u.userId = uc.userId WHERE uc.companyId = :companyId")
    suspend fun getUsersForCompany(companyId: String): List<User>

    @Query("DELETE FROM company_user_cross_ref WHERE userId = :userId AND companyId = :companyId")
    suspend fun removeUserFromCompany(userId: String, companyId: String)

    @Query("SELECT * FROM company_user_cross_ref WHERE userId = :userId AND companyId = :companyId")
    suspend fun getCompanyUserRelation(userId: String, companyId: String): CompanyUsersCrossRefEntity?

    @Query("SELECT COUNT(*) FROM company_user_cross_ref WHERE companyId = :companyId")
    suspend fun getNumberOfUsersForCompany(companyId: String): Int
}