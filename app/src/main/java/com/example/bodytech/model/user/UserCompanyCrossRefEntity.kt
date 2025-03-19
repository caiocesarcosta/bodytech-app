package com.example.bodytech.model.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.bodytech.model.company.Company


@Entity(
    tableName = "user_company_cross_ref",
    primaryKeys = ["userId", "companyId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Company::class,
            parentColumns = ["companyId"],
            childColumns = ["companyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index("companyId")]
)
data class UserCompanyCrossRefEntity(
    val userId: String,
    val companyId: String,
    var hasPermission: Boolean = false // Permissao da empresa ver dados do usuario
)