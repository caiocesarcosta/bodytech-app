package com.example.bodytech.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    /*@Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "bodytech_data"
        ).build()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()

    @Provides
    fun provideUserCompanyDao(appDatabase: AppDatabase) = appDatabase.userCompanyDao()

    @Provides
    fun provideCompanyDao(appDatabase: AppDatabase) = appDatabase.companyDao()

    @Provides
    fun provideCompanyUserDao(appDatabase: AppDatabase) = appDatabase.companyUserDao()*/

}