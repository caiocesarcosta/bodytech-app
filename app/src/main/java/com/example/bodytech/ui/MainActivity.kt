package com.example.bodytech.ui // Ajuste o pacote, se necessário

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bioimpedance.ui.BioImpedanceActivity
import com.example.bodytech.ui.theme.BodyTechTheme
import com.example.bodytech.viewmodel.company.CompanyViewModel
import com.example.bodytech.viewmodel.company.CompanyViewModelImpl
import com.example.bodytech.viewmodel.company.CreateCompanyState
import com.example.bodytech.viewmodel.user.CreateUsersState
import com.example.bodytech.viewmodel.user.UserViewModel
import com.example.bodytech.viewmodel.user.UserViewModelImp
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
//                CreateUsersBtnContent()
//                createCompaniesBtnContent()
                NavigateToBioimpedanceScreen()
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
    fun CreateUsersBtnContent(userViewModel: UserViewModel = hiltViewModel<UserViewModelImp>()) {
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
    @Composable
    fun createCompaniesBtnContent(companiesViewModel: CompanyViewModel = hiltViewModel<CompanyViewModelImpl>()) {
        val coroutineScope = rememberCoroutineScope()
        val createCompanyState by companiesViewModel.createCompaniesStatus.observeAsState(initial = CreateCompanyState.Idle)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    companiesViewModel.createAllCompaniesFromJson()
                }
            }) {
                Text("Criar Companies no Firestore")
            }

            when (createCompanyState) {
                is CreateCompanyState.Success -> {
                    Text("Companies criados com sucesso!")
                }

                is CreateCompanyState.Failure -> {
                    Text("Erro ao criar Companies: ${(createCompanyState as CreateUsersState.Failure).exception?.message}")
                }



                CreateCompanyState.Idle -> {}
                CreateCompanyState.Loading -> {}
                else -> {}
            }
        }

    }

    @Composable
    fun NavigateToBioimpedanceScreen() {
        val context = LocalContext.current
        Button(
            onClick = {
                val intent = Intent(context, BioImpedanceActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Abrir Bioimpedância")
        }
    }

}

