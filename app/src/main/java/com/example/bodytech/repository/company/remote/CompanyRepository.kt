package com.example.bodytech.repository.company.remote

import com.example.bodytech.model.company.Company


interface CompanyRepository {
    suspend fun createAllCompaniesFromJson(): Result<Unit>
    suspend fun createCompany(company: Company): Result<Company>
    suspend fun getCompany(companyId: String): Result<Company>
    suspend fun updateCompany(companyId: String, company: Company): Result<Company>
    suspend fun deleteCompany(companyId: String): Result<Unit>
//    fun getAllCompanies() : Flow<Result<List<Company>>>

}