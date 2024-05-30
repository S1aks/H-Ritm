package com.s1aks.h_ritm.domain

import com.s1aks.h_ritm.data.entities.PrefData
import kotlinx.coroutines.flow.Flow

interface PrefRepository {
    val userPreferences: Flow<PrefData>
    suspend fun savePrefData(data: PrefData)
}