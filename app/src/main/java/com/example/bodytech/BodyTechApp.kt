package com.example.bodytech

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe Application do aplicativo Bodytech.
 * Anotada com @HiltAndroidApp para habilitar a injeção de dependência com Hilt.
 */
@HiltAndroidApp
class BodyTechApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicialização manual e explícita do Firebase SDK
        // Na maioria dos casos, o google-services.json cuida disso.
        // Adicione esta linha se você tiver problemas de inicialização do Firebase.
        FirebaseApp.initializeApp(this)
    }
}