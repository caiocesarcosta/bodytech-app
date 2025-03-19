package com.example.bodytech.di

import com.example.bodytech.data.remote.firestore.CompanyFirestoreApi
import com.example.bodytech.data.remote.firestore.CompanyFirestoreApiImpl
import com.example.bodytech.data.remote.firestore.RetrofitClient
import com.example.bodytech.data.remote.firestore.UserFirestoreApi
import com.example.bodytech.data.remote.firestore.UserFirestoreApiImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val BASE_URL = "https://firestore.googleapis.com/v1/projects/seu-projeto-firebase/databases/(default)/documents/" // URL base da API do Firestore

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return BASE_URL
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(baseUrl: String): RetrofitClient {
        return RetrofitClient(baseUrl)
    }

    @Singleton
    @Provides
    fun provideCompanyFirestoreApi(retrofitClient: RetrofitClient): CompanyFirestoreApi {
        return retrofitClient.createCompanyFirestoreApi()
    }

    @Singleton
    @Provides
    fun provideUserFirestoreApi(retrofitClient: RetrofitClient): UserFirestoreApi {
        return retrofitClient.createUserFirestoreApi()
    }
}

