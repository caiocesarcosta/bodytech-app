package com.example.bodytech.data.remote.firestore

import com.example.bodytech.model.company.Company
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CompanyFirestoreApiImpl @Inject constructor(
    private val firestoreApi: CompanyFirestoreApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): CompanyFirestoreApi {
    override suspend fun createCompany(company: Company) = withContext(ioDispatcher) {
        firestoreApi.createCompany(company)
    }

    override suspend fun getCompany(companyId: String) = withContext(ioDispatcher) {
        firestoreApi.getCompany(companyId)
    }

    override suspend fun updateCompany(companyId: String, company: Company) = withContext(ioDispatcher) {
        firestoreApi.updateCompany(companyId, company)
    }

    override suspend fun deleteCompany(companyId: String) = withContext(ioDispatcher) {
        firestoreApi.deleteCompany(companyId)
    }
}