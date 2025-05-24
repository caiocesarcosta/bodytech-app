package com.example.bodytech.model.user

import com.google.gson.annotations.SerializedName

/**
 * [User] representa uma entidade de usuário.
 * Este modelo é usado para serialização/desserialização com Firestore e Gson.
 *
 * @property id O ID único do usuário. Corresponde ao ID do documento no Firestore (e ao UID do Firebase Auth).
 * @property name O nome completo do usuário.
 * @property email O endereço de e-mail do usuário.
 * @property birthDate A data de nascimento do usuário no formato String.
 * @property gender O gênero do usuário.
 * @property associatedCompanies Uma lista de IDs das empresas às quais o usuário está associado.
 */
data class User(
    @SerializedName("id") val id: String, // Alterado de userId para id
    @SerializedName("name") val name: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("birthDate") val birthDate: String?, // Usando String para compatibilidade JSON
    @SerializedName("gender") val gender: String?,
    // REMOVIDO: password: String? - Senhas são gerenciadas pelo Firebase Authentication, não no Firestore.
    @SerializedName("associatedCompanies") val associatedCompanies: List<String>? // Corrigido para corresponder ao JSON
)
