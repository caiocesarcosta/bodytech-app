package com.example.bodytech.model.user


import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.bodytech.model.company.Company

data class UserWithCompanies(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "companyId",
        associateBy = Junction(
            value = UserCompanyCrossRefEntity::class,
            entityColumn = "hasPermission",
            parentColumn = "userId"
        )
    )
    val companies: List<Company>
)
