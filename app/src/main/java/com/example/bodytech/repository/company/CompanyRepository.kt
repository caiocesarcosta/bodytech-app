package com.example.bodytech.repository.company

import com.example.bodytech.viewmodel.company.CreateCompanyState


interface CompanyRepository {
    suspend fun createAllCompaniesFromJson(): Result<Unit>

}