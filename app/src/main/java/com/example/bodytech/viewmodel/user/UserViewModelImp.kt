package com.example.bodytech.viewmodel.user

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodytech.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModelImp @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context // Injetando o contexto da aplicação
) : ViewModel(), UserViewModel {

    private val _createUsersStatus = MutableLiveData<Result<Unit>>()
    override val createUsersStatus: LiveData<Result<Unit>> = _createUsersStatus

    override fun createAllUsersFromJson() {
        viewModelScope.launch {
            _createUsersStatus.value = userRepository.createAllUsersFromJson(context)
        }
    }
}