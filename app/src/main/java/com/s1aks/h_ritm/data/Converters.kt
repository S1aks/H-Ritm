package com.s1aks.h_ritm.data

import com.s1aks.h_ritm.data.db.HeartDataEntity
import com.s1aks.h_ritm.data.entities.HeartData

fun HeartDataEntity.toHeartData() =
    with(this) { HeartData(id, dateTime, topPressure, lowPressure, pulse) }

fun HeartData.toHeartDataEntity() =
    with(this) { HeartDataEntity(id, dateTime, topPressure, lowPressure, pulse) }

fun List<HeartDataEntity>.toListHeartData() =
    this.map { with(it) { HeartData(id, dateTime, topPressure, lowPressure, pulse) } }
