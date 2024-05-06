package com.s1aks.h_ritm.data.entities

data class HeartData(
    var id: Int,
    val time: String,
    val topPressure: Int,
    val lowPressure: Int,
    val pulse: Int
)