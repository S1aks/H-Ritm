package com.s1aks.h_ritm.utils

import androidx.compose.ui.graphics.Color
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.data.entities.HeartRange

const val DATE_DAY_FORMAT = "dd MMMM y"
const val DATE_TIME_FORMAT = "HH:mm"

val preview_list = listOf(
    HeartData(1, 1715227048000L, 120, 80, 60),
    HeartData(2, 1715236148000L, 120, 74, null),
    HeartData(3, 1715347448000L, 151, 90, 74),
    HeartData(4, 1715347848000L, 110, 70, 47),
    HeartData(5, 1715399048000L, 125, 82, 54),
)

val listItemColors = listOf(
    Color(0x99838383),
    Color(0x9900FF00),
    Color(0x99FFE700),
    Color(0x99FF8400),
    Color(0x99FF0000),
)

val heartRangesByAge = arrayOf(
    HeartRange(0..20, 110..120, 70..80),
    HeartRange(21..40, 120..130, 70..80),
    HeartRange(41..60, 120..140, 70..90),
    HeartRange(61..120, 120..150, 70..90)
)
