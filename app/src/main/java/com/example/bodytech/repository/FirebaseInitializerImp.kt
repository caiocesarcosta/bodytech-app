package com.example.bodytech.repository

import android.content.Context
import com.google.firebase.FirebaseApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseInitializerImp  @Inject constructor(@ApplicationContext private val context: Context) :FirebaseInitializer{
    override fun initialize() {
        FirebaseApp.initializeApp(context)
    }
}