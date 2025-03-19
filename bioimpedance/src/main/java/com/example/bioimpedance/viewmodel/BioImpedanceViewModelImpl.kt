package com.example.bioimpedance.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bioimpedance.model.BioImpedanceData
import com.example.bioimpedance.repository.BioImpedanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BioImpedanceViewModelImpl @Inject constructor(
    private val bioImpedanceRepository: BioImpedanceRepository
) : ViewModel(), BioImpedanceViewModel {

    private val _createBioImpedanceDataStatus =
        MutableLiveData<CreateBioImpedanceState>(CreateBioImpedanceState.Idle)

    override val createBioimpedanceDataStatus: LiveData<CreateBioImpedanceState> =
        _createBioImpedanceDataStatus

    private val _saveStatus = MutableLiveData<SaveCreateBioImpedanceState>(SaveCreateBioImpedanceState.Idle)

    override val saveStatus: LiveData<SaveCreateBioImpedanceState> = _saveStatus


    override fun createAllBioImpedanceDataFromJson() {
        viewModelScope.launch {
            _createBioImpedanceDataStatus.value = CreateBioImpedanceState.Loading

            val result = bioImpedanceRepository.createAllBioImpedanceDataFromJson()

            _createBioImpedanceDataStatus.value = if (result.isSuccess) {
                CreateBioImpedanceState.Success
            } else {
                CreateBioImpedanceState.Failure(result.exceptionOrNull())
            }
        }
    }

    override fun saveBioImpedanceData(data: BioImpedanceData) {
        viewModelScope.launch {

            _saveStatus.value = SaveCreateBioImpedanceState.Loading

            val saveSuccess = bioImpedanceRepository.saveBioImpedanceData(data)

            _saveStatus.value = if (saveSuccess.isSuccess) {
                SaveCreateBioImpedanceState.Success

            } else {
                SaveCreateBioImpedanceState.Failure(saveSuccess.exceptionOrNull())
            }

        }
    }

}

sealed class CreateBioImpedanceState {
    object Idle : CreateBioImpedanceState()
    object Loading : CreateBioImpedanceState()
    object Success : CreateBioImpedanceState()
    data class Failure(val exception: Throwable?) : CreateBioImpedanceState()
}

sealed class SaveCreateBioImpedanceState {
    object Idle : SaveCreateBioImpedanceState()
    object Loading : SaveCreateBioImpedanceState()
    object Success : SaveCreateBioImpedanceState()
    data class Failure(val exception: Throwable?) : SaveCreateBioImpedanceState()
}