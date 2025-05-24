package com.example.bodytech.model.user

data class UserCompanyCrossRefEntity(
    val userId: String,
    val companyId: String,
    var hasPermission: Boolean = false // Permissao da empresa ver dados do usuario
)