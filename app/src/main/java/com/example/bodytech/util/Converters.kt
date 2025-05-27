package com.example.bodytech.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {

    /*@TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { com.google.type.Date(it) }
    }*/

    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    fun fromListOfStrings(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    fun toListOfStrings(list: List<String>): String {
        return Gson().toJson(list)
    }
}