package com.example.bodytech.data.remote.firestore

import com.example.bodytech.data.remote.firestore.CompanyFirestoreApi
import com.example.bodytech.data.remote.firestore.UserFirestoreApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val baseUrl: String
) {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createUserFirestoreApi(): UserFirestoreApi {
        return retrofit.create(UserFirestoreApi::class.java)
    }

    fun createCompanyFirestoreApi(): CompanyFirestoreApi {
        return retrofit.create(CompanyFirestoreApi::class.java)
    }
}