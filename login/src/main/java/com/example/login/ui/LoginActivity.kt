// login/src/main/kotlin/com/example/login/ui/LoginActivity.kt

package com.example.login.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.login.model.auth.LoginType // Verifique o caminho do pacote
import com.example.login.viewmodel.login.LoginUiState
import com.example.login.viewmodel.login.LoginViewModel
import com.example.login.viewmodel.login.LoginViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Chama a função Composable principal da tela de login
                    LoginScreen(viewModel = hiltViewModel<LoginViewModelImpl>())
                }
            }
        }
    }
}

// Função Composable principal da tela de login
@Composable
fun LoginScreen(
    viewModel: LoginViewModel // A ViewModel é passada como parâmetro
) {
    // Observa o estado da UI da ViewModel.
    val uiState by viewModel.uiState.collectAsState()

    // Usa LocalContext para acessar o Context (necessário para Toast)
    val context = LocalContext.current

    // Bloco LaunchedEffect para lidar com efeitos colaterais em resposta a mudanças de estado.
    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Failure -> {
                val errorMessage = (uiState as LoginUiState.Failure).exception?.message ?: "Erro desconhecido."
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                viewModel.onErrorShown() // Notifica a ViewModel que o erro foi mostrado
            }
            is LoginUiState.Success -> {
                // Lógica de navegação aqui. Ex: navegar para a tela principal.
                // Por enquanto, apenas mostramos um Toast.
                val successMessage = "Login bem-sucedido como ${(uiState as LoginUiState.Success).loginType.name}!"
                Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()

                // *** Lógica de Navegação Real ***
                // Aqui você navegaria para a tela principal, possivelmente passando o tipo de login.
                // Ex: if (uiState.loginType == LoginType.USER) { navigateToUserHomeScreen() } else { navigateToCompanyDashboard() }
                // Chamar uma função de navegação definida fora deste Composable,
                // ou usar um NavigationController se estiver usando Navigation Compose.

                viewModel.onLoginSuccessHandled() // Notifica a ViewModel que o sucesso foi tratado
            }
            // Idle e Loading não precisam de efeitos colaterais aqui
            else -> {
                // Outros estados (Idle, Loading)
            }
        }
    }

    // --- Estado local para os campos de texto ---
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    // --- Layout da Tela ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Tela de Login") // Título da tela

        Spacer(modifier = Modifier.height(16.dp))

        // --- Campo de E-mail ---
        OutlinedTextField(
            value = emailText,
            onValueChange = {
                emailText = it
                viewModel.onEmailChanged(it) // Notifica a ViewModel
            },
            label = { Text("E-mail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- Campo de Senha ---
        OutlinedTextField(
            value = passwordText,
            onValueChange = {
                passwordText = it
                viewModel.onPasswordChanged(it) // Notifica a ViewModel
            },
            label = { Text("Senha") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Seleção de Tipo de Usuário ---
        // Precisamos de um estado local ou expor da ViewModel qual tipo está selecionado para mudar a cor do botão
        // Por enquanto, vamos apenas mostrar os botões sem a lógica de cor baseada na seleção
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Botão para selecionar Usuário
            Button(
                onClick = { viewModel.onLoginTypeSelected(LoginType.USER) } // Notifica a ViewModel
                // Você pode adicionar colors = ButtonDefaults.buttonColors(...) com lógica de seleção aqui
            ) {
                Text("Sou Usuário")
            }

            // Botão para selecionar Empresa
            Button(
                onClick = { viewModel.onLoginTypeSelected(LoginType.COMPANY) } // Notifica a ViewModel
                // Você pode adicionar colors = ButtonDefaults.buttonColors(...) com lógica de seleção aqui
            ) {
                Text("Sou Empresa")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // --- Botão de Login ---
        Button(
            onClick = { viewModel.onLoginClick() }, // Chama a função de login na ViewModel
            modifier = Modifier.fillMaxWidth(),
            // Desabilita o botão enquanto o estado for Loading
            enabled = uiState != LoginUiState.Loading
        ) {
            Text("Entrar")
        }

        // --- Indicador de Carregamento ---
        if (uiState is LoginUiState.Loading) {
            CircularProgressIndicator() // Mostra um spinner
        }

        // Notas:
        // - A lógica visual para destacar o tipo de login selecionado não está implementada aqui,
        //   pois requereria expor o 'currentLoginType' da ViewModel para a UI (talvez adicionando-o ao LoginUiState).
        // - A navegação real para a próxima tela após o sucesso precisaria ser implementada (fora deste Composable).
    }
}

// Importe todas as bibliotecas necessárias no topo do arquivo
// Certifique-se que os imports do seu pacote original LoginType e AuthType estão corretos