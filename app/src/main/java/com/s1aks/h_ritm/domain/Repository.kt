package com.s1aks.h_ritm.domain

import com.s1aks.h_ritm.data.entities.HeartData

interface Repository {
    fun getData(id: Int): HeartData
    fun getAllData(): List<HeartData>
    fun insertData(data: HeartData)
    fun updateData(data: HeartData)
    fun deleteData(id: Int)
}