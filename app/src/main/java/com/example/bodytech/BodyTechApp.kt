package com.example.bodytech

import android.app.Application
import android.util.Log
import com.example.bodytech.repository.FirebaseInitializer
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

    @HiltAndroidApp
    class BodyTechApp : Application() {
    }