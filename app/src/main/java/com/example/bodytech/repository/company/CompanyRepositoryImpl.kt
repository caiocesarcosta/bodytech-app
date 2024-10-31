package com.example.bodytech.repository.company
import android.content.Context
import android.util.Log

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
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CompanyRepository {

    private val db = Firebase.firestore

    override suspend fun createAllCompaniesFromJson(): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val jsonFileString = context.assets.open("companies.json").bufferedReader().use { it.readText() }
                val companyType = object : TypeToken<List<Company>>() {}.type
                val companies = Gson().fromJson<List<Company>>(jsonFileString, companyType)

                for (company in companies) {
                    db.collection("companies")
                        .document(company.companyId!!)
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
    // ... (Outras funções do repositório)
}