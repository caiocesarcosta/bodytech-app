package com.example.bodytech.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bodytech.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModelImp @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), UserViewModel {

    private val _createUsersStatus = MutableLiveData<CreateUsersState>(CreateUsersState.Idle)
    override val createUsersStatus: LiveData<CreateUsersState> = _createUsersStatus

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

sealed class CreateUsersState {
    object Idle : CreateUsersState()
    object Loading : CreateUsersState()
    object Success : CreateUsersState()
    data class Failure(val exception: Throwable?) : CreateUsersState()
}