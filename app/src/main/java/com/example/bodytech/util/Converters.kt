package com.example.bodytech.util

import androidx.compose.ui.input.key.type
import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.util.Date

class Converters {

    /*@TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { com.google.type.Date(it) }
    }*/

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromListOfStrings(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toListOfStrings(list: List<String>): String {
        return Gson().toJson(list)
    }
}