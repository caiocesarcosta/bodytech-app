package com.example.bodytech.model
data class Company(
    val companyId: String? = null, // Pode ser nulo se você estiver usando IDs automáticos
    val name: String? = null,
    val address: String? = null,
    val contact: String? = null
    // ... adicione outros campos relevantes para a empresa
) 