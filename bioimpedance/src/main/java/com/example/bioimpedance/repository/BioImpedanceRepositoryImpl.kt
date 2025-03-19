package com.example.bioimpedance.repository

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.bioimpedance.data.local.room.BioimpedanceDataDao
import com.example.bioimpedance.model.BioImpedanceData
import com.example.bioimpedance.model.BioimpedanceDataEntity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class BioImpedanceRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val bioImpedanceDataDao: BioimpedanceDataDao
) : BioImpedanceRepository {

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun saveBioImpedanceData(data: BioImpedanceData): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                // 1. Converter BioimpedanceData para BioimpedanceDataEntity
                val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME // Define o formato ISO 8601
                val date = OffsetDateTime.parse(data.date, formatter) // Faz o parse da data

                val bioimpedanceDataEntity = BioimpedanceDataEntity(
                    userId = data.userId!!,
                    bioimpedanceId = data.bioimpedanceId!!,
                    date = date.toString(),
                    companyId = data.companyId,
                    weight = data.weight!!,
                    height = data.height!!,
                    bmi = data.bmi!!,
                    bodyFat = data.bodyFat!!,
                    muscleMass = data.muscleMass!!
                )

                // 2. Salvar no Firestore (implementação anterior)
                val currentUser = auth.currentUser ?: throw Exception("Usuário não autenticado")
                val userId = currentUser.uid

                val companyId =
                    getCompanyIdForCurrentUser() ?: throw Exception("Empresa não encontrada")

                db.collection("users")
                    .document(userId)
                    .collection("aestheticsData")
                    .document(companyId)
                    .collection("bioimpedanceData")
                    .add(data)
                    .await()

                Log.d(
                    "BioimpedanceRepository",
                    "Dados de bioimpedância salvos com sucesso Firestore!"
                )

                // TODO 3. Salvar no Room, criar função privada
                bioImpedanceDataDao.insert(bioimpedanceDataEntity)
                Log.d("BioimpedanceRepository", "Dados de bioimpedância salvos com sucesso Room!")


                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(
                    "BioimpedanceRepository",
                    "Erro ao salvar dados de bioimpedância: ${e.message}",
                    e
                )
                Result.failure(e)
            }
        }
    }


    override suspend fun createAllBioImpedanceDataFromJson(): Result<Unit> {
        return withContext(ioDispatcher) { // Executa no dispatcher de I/O
            try {
                // Carrega os dados do arquivo JSON
                val jsonFileString = context.assets.open("bioimpedance_data.json").bufferedReader()
                    .use { it.readText() }
                val bioimpedanceDataType = object : TypeToken<List<BioImpedanceData>>() {}.type
                val bioimpedanceDataList =
                    Gson().fromJson<List<BioImpedanceData>>(jsonFileString, bioimpedanceDataType)

                // Itera pelos dados de bioimpedância e cria os documentos no Firestore
                for (bioimpedanceData in bioimpedanceDataList) {
                    db.collection("bioimpedance_data") // Coleção independente
                        .add(bioimpedanceData)
                        .await()

                    Log.d(
                        "BioimpedanceRepository",
                        "Dados de bioimpedância para usuário ${bioimpedanceData.userId} e empresa ${bioimpedanceData.companyId} criados com sucesso!"
                    )
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("BioimpedanceRepository", "Erro ao criar dados de bioimpedância", e)
                Result.failure(e)
            }
        }
    }


    override suspend fun getBioimpedanceData(
        userId: String,
        companyId: String
    ): Flow<List<BioimpedanceDataEntity>> {
        return bioImpedanceDataDao.getAllByUserIdAndCompanyId(userId, companyId)
    }

    override suspend fun updateBioimpedanceData(data: BioimpedanceDataEntity): Result<Unit> {
        return try {
            bioImpedanceDataDao.update(data)
            Result.success(Unit) // Retorna sucesso se não houver exceções
        } catch (e: Exception) {
            Result.failure(e) // Retorna a exceção em caso de erro
        }
    }

    override suspend fun deleteBioimpedanceData(data: BioimpedanceDataEntity): Result<Unit> {
        return try {
            bioImpedanceDataDao.delete(data)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCompanyIdForCurrentUser(): String? {
        return withContext(ioDispatcher) {

            // 1. Obtenha o UID do usuário atual
            // 2. Consulte a coleção "users" para encontrar o documento do usuário
            // 3. Acesse o campo "companies" no documento do usuário
            // 4. Retorne o ID da empresa relevante (por exemplo, o primeiro ID da lista)
            return@withContext null
        }
    }

}

