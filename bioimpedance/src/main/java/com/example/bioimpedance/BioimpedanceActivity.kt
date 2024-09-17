package com.example.bioimpedance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.bioimpedance.ui.theme.BodyTechTheme

class BioimpedanceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BodyTechTheme {
                // A surface container using the 'background' color from the theme
                bioimpedanceScreen()
            }
        }
    }
}
@Composable
fun bioimpedanceScreen() {
    Text("Tela do Módulo de Bioimpedância")
    // Implemente a interface do seu módulo de bioimpedância aqui (campos, botões, etc.)
}