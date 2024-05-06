package com.s1aks.h_ritm.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.h_ritm.data.RepositoryImpl
import com.s1aks.h_ritm.data.entities.HeartData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class MainViewModel : ViewModel() {
    private val repository = RepositoryImpl()
    var dataList: List<HeartData> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    private fun CoroutineScope.doIO(
        block: suspend CoroutineScope.() -> Unit
    ) {
        cancelJob()
        this.launch(Dispatchers.IO) {
            try {
                block()
            } catch (_: CancellationException) {
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }

    }

    fun getData(id: Int) {
        viewModelScope.doIO { dataList = listOf(repository.getData(id)) }
    }

    fun getAllData() {
        viewModelScope.doIO { dataList = repository.getAllData() }
    }

    fun insertData(heartData: HeartData) {
        viewModelScope.doIO {
            repository.insertData(heartData)
            getAllData()
        }
    }

    fun updateData(heartData: HeartData) {
        viewModelScope.doIO {
            repository.updateData(heartData)
            getAllData()
        }
    }

    fun deleteData(data: HeartData) {
        viewModelScope.doIO {
            repository.deleteData(data.id)
            getAllData()
        }
    }

    private fun cancelJob() {
        viewModelScope.coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}
