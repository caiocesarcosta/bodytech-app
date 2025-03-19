package com.example.bodytech.repository.company

import android.content.Context
import android.util.Log
import com.example.bodytech.data.remote.firestore.CompanyFirestoreApiImpl
import com.example.bodytech.model.company.Company
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val companyFirestoreApiImpl: CompanyFirestoreApiImpl
) : CompanyRepository {

    private val db = Firebase.firestore

    override suspend fun createAllCompaniesFromJson(): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val jsonFileString =
                    context.assets.open("companies.json").bufferedReader().use { it.readText() }
                val companyType = object : TypeToken<List<Company>>() {}.type
                val companies = Gson().fromJson<List<Company>>(jsonFileString, companyType)

                for (company in companies) {
                    db.collection("companies")
                        .document(company.companyId!!.toString())
                        .set(company)
                        .await()
                    Log.d("CompanyRepository", "Empresa ${company.name} criada com sucesso!")
                }

                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("CompanyRepository", "Erro ao criar empresas", e)
                Result.failure(e)
            }
        }
    }

    override suspend fun createCompany(company: Company): Result<Company> =
        withContext(ioDispatcher) {
            try {
                val newCompany = companyFirestoreApiImpl.createCompany(company)
                Result.success(newCompany)
            } catch (e: Exception) {
                Result.failure(e)
            } as Result<Company>
        }


    override suspend fun getCompany(companyId: String): Result<Company> =
        withContext(ioDispatcher) {
            try {
                val company = companyFirestoreApiImpl.getCompany(companyId)
                Result.success(company)
            } catch (e: Exception) {
                Result.failure(e)
            } as Result<Company>
        }


    override suspend fun updateCompany(companyId: String, company: Company): Result<Company> =
        withContext(ioDispatcher) {
            try {
                val updatedCompany = companyFirestoreApiImpl.updateCompany(companyId, company)
                Result.success(updatedCompany)
            } catch (e: Exception) {
                Result.failure(e)
            } as Result<Company>
        }


    override suspend fun deleteCompany(companyId: String): Result<Unit> =
        withContext(ioDispatcher) {
            try {
                companyFirestoreApiImpl.deleteCompany(companyId)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    // Retorna um Flow que emite um Result<List<Company>>
    /*override fun getAllCompanies(): Flow<Result<List<Company>>> = flow {
        try {
            emit(Result.success(companyFirestoreDataSource.getAllCompanies()))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }*/
}