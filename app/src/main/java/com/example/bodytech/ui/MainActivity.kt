package com.example.bodytech.ui // Ajuste o pacote, se necessário

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.login.ui.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cria um Intent para iniciar a LoginActivity do módulo login
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent) // Inicia a LoginActivity
        finish()
    }
}