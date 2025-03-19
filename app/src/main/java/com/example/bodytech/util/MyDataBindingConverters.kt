package com.example.bodytech.util

import android.view.View
import androidx.databinding.BindingConversion

object MyDataBindingConverters {
    @JvmStatic
    @BindingConversion
    fun convertStringToBoolean(value: String): Boolean {
        return value.toBoolean()
    }

    @JvmStatic
    @BindingConversion
    fun convertIntToVisibility(value: Int): Int {
        return if (value > 0) View.VISIBLE else View.GONE
    }
}