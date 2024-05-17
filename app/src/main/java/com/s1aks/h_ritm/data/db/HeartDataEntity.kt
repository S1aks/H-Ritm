package com.s1aks.h_ritm.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class HeartDataEntity(
    @PrimaryKey
    var id: Int,
    val dateTime: Long,
    val topPressure: Int,
    val lowPressure: Int,
    val pulse: Int?
)