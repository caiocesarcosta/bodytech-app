package com.example.bodytech.ui // Ajuste o pacote, se necessário

// Importe suas ViewModels e estados de criação de dados

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
import androidx.compose.runtime.livedata.observeAsState
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

        // Comentei ou removi o redirecionamento direto para LoginActivity aqui
        // pois a MainActivity agora terá sua própria UI de "home" de testes.
        // Você iniciará a LoginActivity a partir de um botão nesta UI.

        setContent {
            BodyTechTheme {
                MainTestScreen() // Nossa nova Composable principal para a MainActivity de testes
            }
        }
    }
}

// Enum para controlar qual opção de criação de dados está selecionada
enum class CreationOption {
    NONE, USER, COMPANY
}

@OptIn(ExperimentalMaterial3Api::class) // Anotação para Material3 experimental (OutlinedTextField, etc.)
@Composable
fun MainTestScreen(
    userViewModel: UserViewModel = hiltViewModel<UserViewModelImp>(),
    companyViewModel: CompanyViewModel = hiltViewModel<CompanyViewModelImpl>()
) {
    val context = LocalContext.current // Para Toast messages
    val coroutineScope = rememberCoroutineScope() // Para lançar coroutines

    // Estado para controlar qual opção de criação está selecionada (Usuário, Empresa, Nenhum)
    var selectedCreationOption by remember { mutableStateOf(CreationOption.NONE) }

    // Estados de criação de usuário/empresa da ViewModel
    val createUserStatus by userViewModel.createUsersStatus.observeAsState(initial = CreateUsersState.Idle)
    val createCompanyStatus by companyViewModel.createCompaniesStatus.observeAsState(initial = CreateCompanyState.Idle)

    // Efeitos colaterais para mostrar mensagens de sucesso/falha na criação
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
        // --- Botão "Fazer Login" ---
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

        // --- Caixas de Seleção (Radio Buttons) ---
        Text("Criar Dados de Teste:")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup(), // Torna os RadioButtons mutuamente exclusivos
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // RadioButton para Usuário
            RadioButtonWithText(
                text = "Criar Usuário",
                selected = selectedCreationOption == CreationOption.USER,
                onClick = { selectedCreationOption = CreationOption.USER }
            )

            // RadioButton para Empresa
            RadioButtonWithText(
                text = "Criar Empresa",
                selected = selectedCreationOption == CreationOption.COMPANY,
                onClick = { selectedCreationOption = CreationOption.COMPANY }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Campos de Preenchimento Dinâmicos ---
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

// Composable auxiliar para RadioButton com texto
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
        RadioButton(
            selected = selected,
            onClick = null
        ) // onClick é nulo porque o selectable já lida com o clique
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

// --- Composable para Formulário de Criação de Usuário ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCreationForm(
    userViewModel: UserViewModel,
    coroutineScope: CoroutineScope // Scope para lançar coroutines
) {
    // Campos para preencher os dados do usuário (simplificado para o teste)
    // Para um teste real, você precisaria de TextFields para cada campo
    // Aqui, vamos apenas ter um botão que aciona a função de teste do ViewModel
    Button(
        onClick = {
            coroutineScope.launch {
                userViewModel.createAllUsersFromJson()
            }
        },
        enabled = userViewModel.createUsersStatus.value != CreateUsersState.Loading // Desabilita enquanto estiver carregando
    ) {
        Text("Criar Todos os Usuários do JSON")
    }

    // Opcional: Mostrar CircularProgressIndicator se estiver carregando
    if (userViewModel.createUsersStatus.value == CreateUsersState.Loading) {
        CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
    }
}

// --- Composable para Formulário de Criação de Empresa ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyCreationForm(
    companyViewModel: CompanyViewModel,
    coroutineScope: CoroutineScope // Scope para lançar coroutines
) {
    // Campos para preencher os dados da empresa (simplificado para o teste)
    // Da mesma forma que o usuário, um botão para acionar a criação do JSON
    Button(
        onClick = {
            coroutineScope.launch {
                companyViewModel.createAllCompaniesFromJson()
            }
        },
        enabled = companyViewModel.createCompaniesStatus.value != CreateCompanyState.Loading // Desabilita enquanto estiver carregando
    ) {
        Text("Criar Todas as Empresas do JSON")
    }

    // Opcional: Mostrar CircularProgressIndicator se estiver carregando
    if (companyViewModel.createCompaniesStatus.value == CreateCompanyState.Loading) {
        CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
    }
}