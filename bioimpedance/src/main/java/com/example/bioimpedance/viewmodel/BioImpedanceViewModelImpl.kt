package com.example.bioimpedance.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bioimpedance.model.BioImpedanceData
import com.example.bioimpedance.repository.BioImpedanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BioImpedanceViewModelImpl @Inject constructor(
    private val bioImpedanceRepository: BioImpedanceRepository
) : ViewModel(), BioImpedanceViewModel {

    private val _createBioImpedanceDataStatus =
        MutableLiveData<CreateCompanieState>(CreateCompanieState.Idle)

    override val createBioimpedanceDataStatus: LiveData<CreateCompanieState> =
        _createBioImpedanceDataStatus

    private val _saveStatus = MutableLiveData<SaveCompanieState>(SaveCompanieState.Idle)

    override val saveStatus: LiveData<SaveCompanieState> = _saveStatus


    override fun createAllBioImpedanceDataFromJson() {
        viewModelScope.launch {
            _createBioImpedanceDataStatus.value = CreateCompanieState.Loading

            val result = bioImpedanceRepository.createAllBioImpedanceDataFromJson()

            _createBioImpedanceDataStatus.value = if (result.isSuccess) {
                CreateCompanieState.Success
            } else {
                CreateCompanieState.Failure(result.exceptionOrNull())
            }
        }
    }

    override fun saveBioImpedanceData(data: BioImpedanceData) {
        viewModelScope.launch {

            _saveStatus.value = SaveCompanieState.Loading

            val saveSuccess = bioImpedanceRepository.saveBioImpedanceData(data)

            _saveStatus.value = if (saveSuccess.isSuccess) {
                SaveCompanieState.Success

            } else {
                SaveCompanieState.Failure(saveSuccess.exceptionOrNull())
            }

        }
    }

}

sealed class CreateCompanieState {
    object Idle : CreateCompanieState()
    object Loading : CreateCompanieState()
    object Success : CreateCompanieState()
    data class Failure(val exception: Throwable?) : CreateCompanieState()
}

sealed class SaveCompanieState {
    object Idle : SaveCompanieState()
    object Loading : SaveCompanieState()
    object Success : SaveCompanieState()
    data class Failure(val exception: Throwable?) : SaveCompanieState()
}