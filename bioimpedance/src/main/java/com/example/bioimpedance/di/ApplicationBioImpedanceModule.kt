package com.example.bioimpedance.di

import android.content.Context
import androidx.room.Room
import com.example.bioimpedance.data.local.room.BioimpedanceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApplicationBioImpedanceModule {

//    @Provides
//    @Singleton
//    fun provideBioImpedanceCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideBioimpedanceDatabase(@ApplicationContext context: Context): BioimpedanceDatabase {
        return Room.databaseBuilder(
            context,
            BioimpedanceDatabase::class.java,
            "bioimpedance_data" // Nome do banco de dados
        ).build()
    }

    @Provides
    fun provideBioimpedanceDataDao(bioimpedanceDatabase: BioimpedanceDatabase) = bioimpedanceDatabase.bioimpedanceDataDao()
}
