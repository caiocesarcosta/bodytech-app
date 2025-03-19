package com.example.bodytech.model.company

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.bodytech.model.user.User


data class CompanyWithUsers(
    @Embedded val company: Company,
    @Relation(
        parentColumn = "companyId",
        entityColumn = "userId",
        associateBy = Junction(CompanyUsersCrossRefEntity::class)
    )
    val users: List<User>
)
