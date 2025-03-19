package com.example.bodytech.data.remote.firestore

import com.example.bodytech.model.company.Company
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CompanyFirestoreApi {
    @POST("companies")
    suspend fun createCompany(@Body company: Company): Company

    @PUT("companies/{companyId}")
    suspend fun updateCompany(@Path("CompanyId") companyId: String, @Body company: Company): Company

    @DELETE("companies/{companyId}")
    suspend fun deleteCompany(@Path("companyId") companyDaoId: String)

    @GET("companies/{companyId}")
    suspend fun getCompany(@Path("companyId") companyId: String): Company

}


