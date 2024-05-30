package com.s1aks.h_ritm.domain

import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.data.entities.PrefData

interface DataRepository {
    suspend fun getData(id: Int): HeartData
    suspend fun getAllData(): List<HeartData>
    suspend fun insertData(data: HeartData)
    suspend fun updateData(data: HeartData)
    suspend fun deleteData(id: Int)
}