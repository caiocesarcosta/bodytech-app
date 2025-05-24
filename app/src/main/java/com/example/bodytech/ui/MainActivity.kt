package com.example.bodytech.ui

// Imports para Flow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bodytech.ui.theme.BodyTechTheme
import com.example.bodytech.viewmodel.company.CompanyViewModel
import com.example.bodytech.viewmodel.company.CompanyViewModelImpl
import com.example.bodytech.viewmodel.company.CreateCompanyState
import com.example.bodytech.viewmodel.user.CreateUsersState
import com.example.bodytech.viewmodel.user.UserViewModel
import com.example.bodytech.viewmodel.user.UserViewModelImp
import com.example.login.ui.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BodyTechTheme {
                MainTestScreen()
            }
        }
    }
}

enum class CreationOption {
    NONE, USER, COMPANY
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTestScreen(
    userViewModel: UserViewModel = hiltViewModel<UserViewModelImp>(),
    companyViewModel: CompanyViewModel = hiltViewModel<CompanyViewModelImpl>()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var selectedCreationOption by remember { mutableStateOf(CreationOption.NONE) }

    // Mantemos as coleções aqui para os LaunchedEffects que reagem a mudanças globais
    // (e para o caso de algum outro componente filho precisar do estado)
    val createUserStatus by userViewModel.createUsersStatus.collectAsStateWithLifecycle()
    val createCompanyStatus by companyViewModel.createCompaniesStatus.collectAsStateWithLifecycle()


    LaunchedEffect(createUserStatus) {
        when (createUserStatus) {
            is CreateUsersState.Success -> Toast.makeText(
                context,
                "Usuários criados com sucesso!",
                Toast.LENGTH_SHORT
            ).show()

            is CreateUsersState.Failure -> Toast.makeText(
                context,
                "Erro ao criar usuários: ${(createUserStatus as CreateUsersState.Failure).exception?.message}",
                Toast.LENGTH_LONG
            ).show()

            else -> {}
        }
    }

    LaunchedEffect(createCompanyStatus) {
        when (createCompanyStatus) {
            is CreateCompanyState.Success -> Toast.makeText(
                context,
                "Empresas criadas com sucesso!",
                Toast.LENGTH_SHORT
            ).show()

            is CreateCompanyState.Failure -> Toast.makeText(
                context,
                "Erro ao criar empresas: ${(createCompanyStatus as CreateCompanyState.Failure).exception?.message}",
                Toast.LENGTH_LONG
            ).show()

            else -> {}
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Fazer Login")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Criar Dados de Teste:")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButtonWithText(
                text = "Criar Usuário",
                selected = selectedCreationOption == CreationOption.USER,
                onClick = { selectedCreationOption = CreationOption.USER }
            )

            RadioButtonWithText(
                text = "Criar Empresa",
                selected = selectedCreationOption == CreationOption.COMPANY,
                onClick = { selectedCreationOption = CreationOption.COMPANY }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedCreationOption) {
            CreationOption.USER -> {
                UserCreationForm(userViewModel, coroutineScope)
            }

            CreationOption.COMPANY -> {
                CompanyCreationForm(companyViewModel, coroutineScope)
            }

            CreationOption.NONE -> {
                Text("Selecione uma opção acima para exibir os campos de criação.")
            }
        }
    }
}

@Composable
fun RadioButtonWithText(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = null)
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCreationForm(
    userViewModel: UserViewModel,
    coroutineScope: CoroutineScope
) {
    // CORREÇÃO AQUI: Coletar o StateFlow dentro deste Composable para usar o estado reativamente
    val createUserStatus by userViewModel.createUsersStatus.collectAsStateWithLifecycle()

    Button(
        onClick = {
            coroutineScope.launch {
                userViewModel.createAllUsersFromJson()
            }
        },
        // Usar a variável coletada 'createUserStatus'
        enabled = createUserStatus != CreateUsersState.Loading
    ) {
        Text("Criar Todos os Usuários do JSON")
    }

    // Usar a variável coletada 'createUserStatus'
    if (createUserStatus == CreateUsersState.Loading) {
        CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyCreationForm(
    companyViewModel: CompanyViewModel,
    coroutineScope: CoroutineScope
) {
    // CORREÇÃO AQUI: Coletar o StateFlow dentro deste Composable para usar o estado reativamente
    val createCompanyStatus by companyViewModel.createCompaniesStatus.collectAsStateWithLifecycle()

    Button(
        onClick = {
            coroutineScope.launch {
                companyViewModel.createAllCompaniesFromJson()
            }
        },
        // Usar a variável coletada 'createCompanyStatus'
        enabled = createCompanyStatus != CreateCompanyState.Loading
    ) {
        Text("Criar Todas as Empresas do JSON")
    }

    // Usar a variável coletada 'createCompanyStatus'
    if (createCompanyStatus == CreateCompanyState.Loading) {
        CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
    }
}