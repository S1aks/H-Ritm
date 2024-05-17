package com.s1aks.h_ritm.ui.screens.datalist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.ui.MainViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DataListScreen(viewModel: MainViewModel) {
    if (viewModel.dataList.isNotEmpty()) {
        DataList(list = viewModel.dataList)
    } else {
        DataList(list = preview_list.sortedByDescending { it.dateTime })
    }
    //Text(text = "Пусто")
}

private val preview_list = listOf(
    HeartData(1, 1715227048000L, 120, 80, 60),
    HeartData(2, 1715236148000L, 120, 74, null),
    HeartData(3, 1715347448000L, 151, 90, 74),
    HeartData(4, 1715347848000L, 110, 70, 47),
    HeartData(5, 1715399048000L, 125, 82, 54),
)

private val listItemColors = listOf(
    Color(0x99838383),
    Color(0x9900FF00),
    Color(0x99FFE700),
    Color(0x99FF8400),
    Color(0x99FF0000),
)

const val DATE_DAY_FORMAT = "dd MMMM y"
const val DATE_TIME_FORMAT = "HH:mm"

val Long.getDate: String
    @RequiresApi(Build.VERSION_CODES.O)
    get() = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(DATE_DAY_FORMAT))

val Long.getTime: String
    @RequiresApi(Build.VERSION_CODES.O)
    get() = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))

private data class HeartRange(
    val ageRange: IntRange,
    val topRange: IntRange,
    val lowRange: IntRange
)

private val heartRangesByAge = arrayOf(
    HeartRange(0..20, 110..120, 70..80),
    HeartRange(21..40, 120..130, 70..80),
    HeartRange(41..60, 120..140, 70..90),
    HeartRange(61..120, 120..150, 70..90)
)

private const val AGE = 45

private fun HeartData.getColor(): Color =
    with(this) {
        val heartRange = heartRangesByAge.first { AGE in it.ageRange }
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

@Composable
fun DateItem(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        listItemColors[0],
                        Color.Transparent,
//                        listItemColors[0]
                    )
                )
            )
            .padding(horizontal = 12.dp, vertical = 2.dp),
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic,
        text = text
    )
    HorizontalDivider(color = Color.DarkGray)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListItem(heartData: HeartData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        heartData.getColor(),
                        heartData.getColor(),
                        Color.Transparent
                    )
                ),
            )
            .padding(horizontal = 12.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            fontSize = 20.sp,
            text = heartData.dateTime.getTime,
        )
        Text(
            fontSize = 30.sp,
            text = "${heartData.topPressure} / ${heartData.lowPressure}"
        )
        Text(
            modifier = Modifier.width(60.dp),
            fontSize = 24.sp,
            text = "♡ ${heartData.pulse ?: " -"}"
        )
    }
    HorizontalDivider(color = Color.DarkGray)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DataList(list: List<HeartData>) {
    Column {
        HorizontalDivider(color = Color.DarkGray)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(list) { listItem ->
                val indexItem = list.indexOf(listItem)
                val currentDate = listItem.dateTime.getDate
                val prevDate = if (indexItem > 0) {
                    list[indexItem - 1].dateTime.getDate
                } else currentDate
                if (indexItem == 0 || currentDate != prevDate) DateItem(currentDate)
                ListItem(heartData = listItem)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DataListPreview() {
    DataList(list = preview_list.sortedByDescending { it.dateTime })
}
