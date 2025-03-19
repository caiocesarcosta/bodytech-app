package com.example.bodytech.data.remote.firestore

import com.example.bodytech.model.company.Company
import com.example.bodytech.model.user.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserFirestoreApi {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): User


    @POST("users")
    suspend fun createUser(@Body user: User): User

    @PUT("users/{userId}")
    suspend fun updateUser(@Path("userId") userId: String, @Body user: User): User

    @DELETE("users/{UserId}")
    suspend fun deleteUser(@Path("userId") userId: String)


}