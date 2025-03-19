package com.example.bodytech.model.company

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.bodytech.model.user.User

@Entity(
    tableName = "company_user_cross_ref",
    primaryKeys = ["companyId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = ["companyId"],
            childColumns = ["companyId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("companyId"), Index("userId")]
)
data class CompanyUsersCrossRefEntity(
    val companyId: String,
    val userId: String
)
