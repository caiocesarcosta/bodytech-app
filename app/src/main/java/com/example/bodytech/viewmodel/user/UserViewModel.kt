package com.example.bodytech.viewmodel.user

import kotlinx.coroutines.flow.StateFlow // Importar StateFlow

/**
 * Interface para a ViewModel de [User].
 * Define as operações e o estado de criação de usuários.
 */
interface UserViewModel {
    /**
     * Inicia a criação de todos os usuários a partir de um arquivo JSON.
     */
    fun createAllUsersFromJson()

    /**
     * O estado atual da operação de criação de usuários.
     * É um [StateFlow] que emite atualizações do status (Idle, Loading, Success, Failure).
     */
    val createUsersStatus: StateFlow<CreateUsersState> // Alterado de LiveData para StateFlow
}