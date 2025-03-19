package com.example.bioimpedance.viewmodel

import androidx.lifecycle.LiveData
import com.example.bioimpedance.model.BioImpedanceData

interface BioImpedanceViewModel {
    fun createAllBioImpedanceDataFromJson()
    val createBioimpedanceDataStatus: LiveData<CreateBioImpedanceState>
    fun saveBioImpedanceData(data: BioImpedanceData)
    val saveStatus: LiveData<SaveCreateBioImpedanceState>
}