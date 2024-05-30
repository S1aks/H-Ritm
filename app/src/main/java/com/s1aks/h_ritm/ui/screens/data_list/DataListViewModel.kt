package com.s1aks.h_ritm.ui.screens.data_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.h_ritm.data.DataRepositoryImpl
import com.s1aks.h_ritm.data.PrefRepositoryIml
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.data.entities.PrefData
import com.s1aks.h_ritm.domain.DataRepository
import com.s1aks.h_ritm.domain.PrefRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataListViewModel(
    private val prefRepository: PrefRepository = PrefRepositoryIml(),
    private val dataRepository: DataRepository = DataRepositoryImpl()
) : ViewModel() {
    private val _data = MutableStateFlow<List<HeartData>>(listOf())
    val data: StateFlow<List<HeartData>> = _data.asStateFlow()
    val prefData: StateFlow<PrefData?> = prefRepository.userPreferences.stateIn(
        viewModelScope, SharingStarted.Eagerly, null,
    )

    fun getAllData() {
        viewModelScope.launch {
            _data.value = withContext(Dispatchers.IO) { dataRepository.getAllData() }
        }
    }

    fun deleteData(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataRepository.deleteData(id)
            }
        }
        getAllData()
    }
}
