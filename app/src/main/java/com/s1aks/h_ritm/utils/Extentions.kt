package com.s1aks.h_ritm.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.s1aks.h_ritm.data.entities.HeartData
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val Long.getDate: String
    @RequiresApi(Build.VERSION_CODES.O)
    get() = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(DATE_DAY_FORMAT))

val Long.getTime: String
    @RequiresApi(Build.VERSION_CODES.O)
    get() = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))

fun HeartData.getColor(age: Int): Color =
    with(this) {
        val heartRange = heartRangesByAge.first { age in it.ageRange }
        when {
            topPressure < heartRange.topRange.first - 10
                    || topPressure > heartRange.topRange.last + 10
                    || lowPressure < heartRange.lowRange.first - 10
                    || lowPressure > heartRange.lowRange.last + 10
                    || (pulse != null && (pulse < 45 || pulse > 105)) -> listItemColors[4]

            topPressure < heartRange.topRange.first - 5
                    || topPressure > heartRange.topRange.last + 5
                    || lowPressure < heartRange.lowRange.first - 5
                    || lowPressure > heartRange.lowRange.last + 5
                    || (pulse != null && (pulse < 50 || pulse > 100)) -> listItemColors[3]

            topPressure < heartRange.topRange.first
                    || topPressure > heartRange.topRange.last
                    || lowPressure < heartRange.lowRange.first
                    || lowPressure > heartRange.lowRange.last
                    || (pulse != null && (pulse < 55 || pulse > 95)) -> listItemColors[2]

            else -> listItemColors[1]
        }
    }