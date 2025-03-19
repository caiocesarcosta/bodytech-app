package com.example.bodytech.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bodytech.data.local.room.CompanyDao
import com.example.bodytech.data.local.room.UserDao
import com.example.bodytech.data.local.room.company.CompanyUserDao
import com.example.bodytech.data.local.room.user.UserCompanyDao
import com.example.bodytech.model.company.Company
import com.example.bodytech.model.company.CompanyUsersCrossRefEntity
import com.example.bodytech.model.user.User
import com.example.bodytech.model.user.UserCompanyCrossRefEntity
import com.example.bodytech.util.Converters

@Database(
    entities = [
        User::class,
        Company::class,
        UserCompanyCrossRefEntity::class,
        CompanyUsersCrossRefEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userCompanyDao(): UserCompanyDao
    abstract fun companyDao(): CompanyDao
    abstract fun companyUserDao(): CompanyUserDao
}