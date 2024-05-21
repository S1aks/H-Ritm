package com.s1aks.h_ritm.ui.screens.datalist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.h_ritm.data.RepositoryImpl
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataListViewModel(
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {
    private val _data = MutableStateFlow<List<HeartData>>(listOf())
    val data: StateFlow<List<HeartData>> = _data.asStateFlow()

    fun getAllData() {
        viewModelScope.launch {
            _data.value = withContext(Dispatchers.IO) { repository.getAllData() }
        }
    }

    fun deleteData(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteData(id)
            }
        }
        getAllData()
    }
}
