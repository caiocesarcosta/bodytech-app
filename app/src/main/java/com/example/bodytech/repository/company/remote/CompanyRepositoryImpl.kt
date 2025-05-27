package com.example.bodytech.repository.company.remote

import android.content.Context
import android.util.Log
import com.example.bodytech.model.company.Company
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação de [CompanyRepository] para interação com o Firestore.
 * Gerencia operações de CRUD para documentos da coleção 'companies'.
 * Utiliza Gson para carregar dados de empresas a partir de um arquivo JSON.
 *
 * @param context O contexto da aplicação, injetado por Hilt.
 * @param ioDispatcher O CoroutineDispatcher para operações de I/O.
 */
class CompanyRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CompanyRepository {

    private val db = Firebase.firestore
    private val companiesCollection = db.collection("companies") // Referência à coleção de empresas

    /**
     * Cria todas as empresas a partir do arquivo 'companies.json' nos assets.
     * Cada empresa é adicionada como um documento no Firestore, usando seu 'id' como Document ID.
     * Se uma empresa com o mesmo ID já existir, ela será sobrescrita.
     */
    override suspend fun createAllCompaniesFromJson(): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val jsonFileString = context.assets.open("companies.json").bufferedReader().use { it.readText() }
                val companyType = object : TypeToken<List<Company>>() {}.type
                val companies = Gson().fromJson<List<Company>>(jsonFileString, companyType)

                for (company in companies) {
                    companiesCollection.document(company.id) // Usa o 'id' da empresa como ID do documento
                        .set(company) // .set() sobrescreve se existir, cria se não
                        .await()
                    Log.d("CompanyRepositoryImp", "Empresa ${company.id} criada/atualizada com sucesso!")
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("CompanyRepositoryImp", "Erro ao criar empresas a partir do JSON", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Cria um novo documento de empresa no Firestore.
     * @param company O objeto [Company] a ser criado. O 'id' da empresa será usado como ID do documento.
     * @return [Result.success] com a empresa criada se a operação for bem-sucedida, [Result.failure] caso contrário.
     */
    override suspend fun createCompany(company: Company): Result<Company> {
        return withContext(ioDispatcher) {
            try {
                companiesCollection.document(company.id)
                    .set(company)
                    .await()
                Result.success(company)
            } catch (e: Exception) {
                Log.e("CompanyRepositoryImp", "Erro ao criar empresa ${company.id}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Obtém um documento de empresa pelo seu ID.
     * @param companyId O ID da empresa a ser buscada.
     * @return [Result.success] com o objeto [Company] se encontrado, [Result.failure] se não encontrado ou ocorrer um erro.
     */
    override suspend fun getCompany(companyId: String): Result<Company> {
        return withContext(ioDispatcher) {
            try {
                val documentSnapshot = companiesCollection.document(companyId).get().await()
                val company = documentSnapshot.toObject(Company::class.java)
                if (company != null) {
                    Result.success(company)
                } else {
                    Result.failure(NoSuchElementException("Empresa com ID $companyId não encontrada."))
                }
            } catch (e: Exception) {
                Log.e("CompanyRepositoryImp", "Erro ao obter empresa $companyId", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Atualiza um documento de empresa existente no Firestore.
     * @param companyId O ID da empresa a ser atualizada.
     * @param company O objeto [Company] com os dados atualizados. O 'id' deve corresponder ao companyId.
     * @return [Result.success] com a empresa atualizada se a operação for bem-sucedida, [Result.failure] caso contrário.
     */
    override suspend fun updateCompany(companyId: String, company: Company): Result<Company> {
        return withContext(ioDispatcher) {
            try {
                // Garante que o ID do objeto corresponde ao ID do documento
                if (company.id != companyId) {
                    return@withContext Result.failure(IllegalArgumentException("O ID da empresa no objeto não corresponde ao ID do documento fornecido."))
                }
                companiesCollection.document(companyId)
                    .set(company) // .set() com um objeto completo sobrescreve o documento
                    .await()
                Result.success(company)
            } catch (e: Exception) {
                Log.e("CompanyRepositoryImp", "Erro ao atualizar empresa $companyId", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Deleta um documento de empresa do Firestore.
     * @param companyId O ID da empresa a ser deletada.
     * @return [Result.success] se a empresa for deletada com sucesso, [Result.failure] caso contrário.
     */
    override suspend fun deleteCompany(companyId: String): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                companiesCollection.document(companyId)
                    .delete()
                    .await()
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("CompanyRepositoryImp", "Erro ao deletar empresa $companyId", e)
                Result.failure(e)
            }
        }
    }
}