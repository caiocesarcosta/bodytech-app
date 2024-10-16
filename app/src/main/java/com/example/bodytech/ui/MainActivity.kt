package com.example.bodytech.ui // Ajuste o pacote, se necessário

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bodytech.ui.theme.BodyTechTheme
import com.example.bodytech.viewmodel.user.CreateUsersState
import com.example.bodytech.viewmodel.user.UserViewModel
import com.example.bodytech.viewmodel.user.UserViewModelImp
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//        private val userViewModel: UserViewModel by viewModels()

//    private lateinit var binding: MainActivity
//    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this) // Inicializa o Firebase aqui


        setContent {


            BodyTechTheme {
                Log.d("TAG", "BodyTechTheme")
                MainScreenContent()
//                bodyTechApp()
            }
        }
    }


//    @Composable
//    fun bodyTechApp() {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Log.d("TAG", "BodyTechApp")
//            MainScreenContent() // Chame a função MainScreenContent aqui
//        }
//    }


    @Composable
    fun MainScreenContent(userViewModel: UserViewModel = hiltViewModel<UserViewModelImp>()) {
//    fun MainScreenContent() {
        val coroutineScope = rememberCoroutineScope()
        val createUsersStatus by userViewModel.createUsersStatus.observeAsState(initial = CreateUsersState.Idle)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    userViewModel.createAllUsersFromJson()
                }
            }) {
                Text("Criar Usuários no Firestore")
            }

            when (createUsersStatus) {
                is CreateUsersState.Success -> {
                    Text("Usuários criados com sucesso!")
                }

                is CreateUsersState.Failure -> {
                    Text("Erro ao criar usuários: ${(createUsersStatus as CreateUsersState.Failure).exception?.message}")
                }

                CreateUsersState.Idle -> {}
                CreateUsersState.Loading -> {}
                else -> {}
            }
        }

    }

}
