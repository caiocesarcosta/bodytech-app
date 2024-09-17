package com.example.bodytech

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bodytech.ui.theme.BodyTechTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BodyTechTheme {
                Log.d("TAG", "BodyTechTheme")
                // A surface container using the 'background' color from the theme
                BodyTechApp()
            }
        }
    }

    @Composable
    fun BodyTechApp() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Log.d("TAG", "BodyTechApp")
            OpenBioimpedanceScreen()
        }
    }

    // Função Composable para abrir o módulo de bioimpedância
    @Composable
    fun OpenBioimpedanceScreen() {
        val context = LocalContext.current
        Log.d("TAG", "OpenBioimpedanceScreen")
        Button(
            onClick = {
                val intent =
                    Intent(context, Class.forName("com.example.bioimpedance.BioimpedanceActivity"))
                context.startActivity(intent)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Abrir Módulo de Bioimpedância")
        }
    }

}