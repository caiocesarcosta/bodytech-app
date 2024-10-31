package com.example.bioimpedance.ui

import android.os.Bundle
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bioimpedance.ui.theme.BodyTechTheme
import com.example.bioimpedance.viewmodel.BioImpedanceViewModel
import com.example.bioimpedance.viewmodel.BioImpedanceViewModelImpl
import com.example.bioimpedance.viewmodel.CreateCompanieState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BioImpedanceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BodyTechTheme {
                // A surface container using the 'background' color from the theme
                bioImpedanceScreen()
            }
        }
    }
}

//MainScreenContent(userViewModel: UserViewModel = hiltViewModel<UserViewModelImp>())
@Composable
fun bioImpedanceScreen(bioImpedanceViewModel: BioImpedanceViewModel = hiltViewModel<BioImpedanceViewModelImpl>()) {
    Text("Tela do Módulo de Bioimpedância")
    // Implemente a interface do seu módulo de bioimpedância aqui (campos, botões, etc.)

    val coroutineScope = rememberCoroutineScope()
    val createCompanieState by bioImpedanceViewModel.createBioimpedanceDataStatus.observeAsState(
        initial = CreateCompanieState.Idle
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            coroutineScope.launch {
                bioImpedanceViewModel.createAllBioImpedanceDataFromJson()
            }
        }) {
            Text("Criar data BioImpedance no Firestore")
        }

        when (createCompanieState) {
            is CreateCompanieState.Success -> {
                Text("BioImpedanceData criados com sucesso!")
            }

            is CreateCompanieState.Failure -> {
                Text("Erro ao criar BioImpedanceData: ${(createCompanieState as CreateCompanieState.Failure).exception?.message}")
            }

            CreateCompanieState.Idle -> {}
            CreateCompanieState.Loading -> {}
            else -> {}
        }
    }
}