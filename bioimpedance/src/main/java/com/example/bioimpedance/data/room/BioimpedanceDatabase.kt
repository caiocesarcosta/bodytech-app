package com.example.bioimpedance.data.room

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bioimpedance.model.BioimpedanceDataEntity
import java.time.OffsetDateTime

@Database(entities = [BioimpedanceDataEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BioimpedanceDatabase : RoomDatabase() {

    abstract fun bioimpedanceDataDao(): BioimpedanceDataDao

    companion object {
        @Volatile
        private var INSTANCE: BioimpedanceDatabase? = null

        fun getDatabase(context: Context): BioimpedanceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    BioimpedanceDatabase::class.java,
                    "bioimpedance_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @androidx.room.TypeConverter
    fun fromTimestamp(value: Long?): OffsetDateTime? {
        return value?.let { OffsetDateTime.from(java.time.Instant.ofEpochMilli(it)) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @androidx.room.TypeConverter
    fun dateToTimestamp(date: OffsetDateTime?): Long? {
        return date?.toInstant()?.toEpochMilli()
    }
}