package com.example.bodytech.viewmodel.company

import androidx.lifecycle.LiveData

interface CompanyViewModel {
    fun createAllCompaniesFromJson()
    val createCompaniesStatus: LiveData<CreateCompanyState>
}