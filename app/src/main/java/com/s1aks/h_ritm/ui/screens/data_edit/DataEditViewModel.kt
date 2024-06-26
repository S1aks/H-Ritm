package com.s1aks.h_ritm.ui.screens.data_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.h_ritm.data.DataRepositoryImpl
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.domain.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataEditViewModel(
    private val repository: DataRepository = DataRepositoryImpl()
) : ViewModel() {
    private val _data = MutableStateFlow<HeartData?>(null)
    val data: StateFlow<HeartData?> = _data.asStateFlow()

    fun getData(id: Int) {
        viewModelScope.launch {
            _data.value = withContext(Dispatchers.IO) { repository.getData(id) }
        }
    }

    fun insertData(heartData: HeartData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { repository.insertData(heartData) }
        }
    }

    fun updateData(heartData: HeartData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { repository.updateData(heartData) }
        }
    }
}
