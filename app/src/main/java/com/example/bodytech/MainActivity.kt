package com.example.bodytech

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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
        val auth = Firebase.auth
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Autenticação anônima bem-sucedida, agora você pode criar os dados no Firestore
                    Log.d("MainActivity", "Autenticação anônima bem-sucedida!")
                    createFirestoreData() // Chama a função para criar os dados
                    setContent {
                        BodyTechTheme {
                            BodyTechApp()
                        }
                    }
                } else {
                    // Trate o erro de autenticação
                    Log.w("MainActivity", "Falha na autenticação anônima", task.exception)
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

                createFirestoreData()

//                val intent =
//                    Intent(context, Class.forName("com.example.bioimpedance.BioimpedanceActivity"))
//                context.startActivity(intent)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Create db Firestore")
        }
    }

    private fun createFirestoreData() {
        CoroutineScope(Dispatchers.IO).launch { // Use Dispatchers.IO para operações de I/O
            val db = Firebase.firestore

            try {
                // Criar dados da empresa
                val companyData = hashMapOf(
                    "companyId" to "beautyCompany123",
                    "name" to "Beauty Company",
                    "address" to "123 Flower Street",
                    "contact" to "contact@beautycompany.com"
                )
                db.collection("companies").document("beautyCompany123")
                    .set(companyData)
                    .await()
                Log.d("MainActivity", "Dados da empresa criados com sucesso!")

                // Criar dados do usuário
                val userData = hashMapOf(
                    "userId" to "user1",
                    "name" to "Ana Maria",
                    "email" to "ana.maria@email.com",
                    "birthDate" to "1985-08-20",
                    "gender" to "female",
                    "companies" to listOf("beautyCompany123"),
                    "aestheticsData" to hashMapOf(
                        "companyId" to "beautyCompany123",
                        "shared" to false,
                        "bioimpedanceData" to listOf<Map<String, Any>>()
                    ),
                    "gymData" to hashMapOf(
                        "companyId" to "gymCompany456", // Adicione o ID da academia
                        "shared" to false
                    ),
                    "nutritionData" to hashMapOf(
                        "companyId" to "nutritionCompany789", // Adicione o ID da nutrição
                        "shared" to false
                    )
                )
                db.collection("users").document("user1")
                    .set(userData)
                    .await()
                Log.d("MainActivity", "Dados do usuário criados com sucesso!")

            } catch (e: Exception) {
                Log.e("MainActivity", "Erro ao criar dados no Firestore", e)
            }
        }
    }

}