package com.example.bodytech.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodytech.repository.user.remote.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow // Importar MutableStateFlow
import kotlinx.coroutines.flow.StateFlow    // Importar StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Implementação concreta de [UserViewModel].
 * Responsável por gerenciar o estado da UI relacionado à criação de usuários
 * e interagir com o [UserRepository].
 *
 * @param userRepository O repositório para operações de dados de usuários.
 */
@HiltViewModel
class UserViewModelImp @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), UserViewModel {

    // Alterado de MutableLiveData para MutableStateFlow
    private val _createUsersStatus = MutableStateFlow<CreateUsersState>(CreateUsersState.Idle)
    override val createUsersStatus: StateFlow<CreateUsersState> = _createUsersStatus // Expondo como StateFlow

    /**
     * Inicia a operação de criação de todos os usuários a partir do JSON.
     * Atualiza o [createUsersStatus] durante o processo.
     */
    override fun createAllUsersFromJson() {
        viewModelScope.launch {
            _createUsersStatus.value = CreateUsersState.Loading

            val result = userRepository.createAllUsersFromJson()

            _createUsersStatus.value = if (result.isSuccess) {
                CreateUsersState.Success
            } else {
                CreateUsersState.Failure(result.exceptionOrNull())
            }
        }
    }
}

