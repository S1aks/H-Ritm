package com.s1aks.h_ritm.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HeartData(
    @PrimaryKey
    var id: Int,
    val dateTime: Long,
    val topPressure: Int,
    val lowPressure: Int,
    val pulse: Int?
)