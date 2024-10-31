package com.example.bodytech.repository.company


interface CompanyRepository {
    suspend fun createAllCompaniesFromJson(): Result<Unit>
    // ... (Outras funções que você precisar)
}