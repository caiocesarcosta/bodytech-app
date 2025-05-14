package com.example.login.ui // Mantenha seu pacote original

import android.os.Bundle
import androidx.activity.ComponentActivity // Importe ComponentActivity
import androidx.activity.compose.setContent // Importe setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint // Importe AndroidEntryPoint

// Importe as bibliotecas necessárias:
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text // Importe Text

/**
 * Activity host para a tela de login.
 * Utiliza Compose para construir a interface de usuário.
 * Anotada com @AndroidEntryPoint para permitir injeção de dependência com Hilt.
 */
@AndroidEntryPoint // Anotação Hilt para injetar dependências nesta Activity
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // Define o conteúdo da Activity usando Compose
            // Utilize o tema do seu aplicativo aqui (provavelmente definido no módulo app ou em um módulo comum)
            MaterialTheme { // Substitua por MaterialTheme do seu projeto, se houver um tema customizado
                Surface( // Um contêiner básico com a cor de fundo do tema
                    modifier = Modifier.fillMaxSize(), // Preenche o tamanho máximo disponível
                    color = MaterialTheme.colorScheme.background // Usa a cor de fundo do tema
                ) {
                    // Aqui é onde chamaremos nossa função Composable da tela de login
                    // Por enquanto, podemos colocar um texto simples
                    LoginScreenContent() // Chamada para a nossa função Composable principal da tela de login
                }
            }
        }
    }
}

// Função Composable básica que representará o conteúdo da tela de login (por enquanto)
@Composable // Anotação para funções Composable
fun LoginScreenContent() {
    // Futuramente, aqui construiremos a UI completa da tela de login
    // (campos de texto, botão, indicador de carregamento, mensagens de erro, etc.)
    // utilizando a ViewModel.
    Text("Tela de Login (Em Construção)") // Um texto simples para começar
}

