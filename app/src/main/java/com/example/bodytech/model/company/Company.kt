package com.example.bodytech.model.company

import com.google.gson.annotations.SerializedName

/**
 * [Company] representa uma entidade de empresa.
 * Este modelo é usado para serialização/desserialização com Firestore e Gson.
 *
 * @property id O ID único da empresa. Corresponde ao ID do documento no Firestore.
 * @property name O nome da empresa.
 * @property address O endereço da empresa.
 * @property contactEmail O e-mail de contato da empresa.
 * @property services Uma lista de serviços oferecidos pela empresa.
 */
data class Company(
    @SerializedName("id") val id: String, // Alterado de companyId para id
    @SerializedName("name") val name: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("contactEmail") val contactEmail: String?, // Alterado de contact para contactEmail
    @SerializedName("services") val services: List<String>?
)