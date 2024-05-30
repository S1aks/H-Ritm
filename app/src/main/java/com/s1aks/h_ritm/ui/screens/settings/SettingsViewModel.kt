package com.s1aks.h_ritm.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.h_ritm.data.PrefRepositoryIml
import com.s1aks.h_ritm.data.entities.PrefData
import com.s1aks.h_ritm.domain.PrefRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val repository: PrefRepository = PrefRepositoryIml()
) : ViewModel() {
    val data: StateFlow<PrefData?> = repository.userPreferences.stateIn(
        viewModelScope, SharingStarted.Eagerly, null,
    )

    fun savePrefData(data: PrefData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { repository.savePrefData(data) }
        }
    }
}
