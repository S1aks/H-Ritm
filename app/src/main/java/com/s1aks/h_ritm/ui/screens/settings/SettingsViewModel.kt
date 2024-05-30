package com.s1aks.h_ritm.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.h_ritm.data.RepositoryImpl
import com.s1aks.h_ritm.data.entities.PrefData
import com.s1aks.h_ritm.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {
    private val _data = MutableStateFlow<PrefData?>(null)
    val data: StateFlow<PrefData?> = _data.asStateFlow()

    fun getData() {
        viewModelScope.launch {
            _data.value = withContext(Dispatchers.IO) { repository.getPrefData() }
        }
    }

    fun saveData(data: PrefData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { repository.savePrefData(data) }
        }
    }
}
