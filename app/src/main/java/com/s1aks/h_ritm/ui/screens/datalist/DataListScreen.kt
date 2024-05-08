package com.s1aks.h_ritm.ui.screens.datalist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.ui.MainViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun DataListScreen(viewModel: MainViewModel) {
    DataList(list = viewModel.dataList)
}

private val preview_list = listOf(
    HeartData(1, "15:00", 120, 80, 60),
    HeartData(2, "16:30", 120, 74, 65),
    HeartData(3, "17:27", 150, 90, 74),
    HeartData(4, "18:40", 110, 70, 47),
    HeartData(5, "19:43", 125, 82, 54),
)

private val ListItemColors = listOf(
    Color(0xFFA0FFA0),
    Color(0xFFD0FFA0),
    Color(0xFFFFF6A0),
    Color(0xFFFFD1A0),
    //Color(0xFFFFB6A0),
    Color(0xFFFFA0A0),
)

const val DATE_DAY_FORMAT = "dd MMMM"
const val DATE_TIME_FORMAT = "HH:mm"

val String.millisGetDate: String
    @RequiresApi(Build.VERSION_CODES.O)
    get() = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.toLong()), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(DATE_DAY_FORMAT))

val String.millisGetTime: String
    @RequiresApi(Build.VERSION_CODES.O)
    get() = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.toLong()), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))

private fun HeartData.getColor(): Color =
    with(this) {
        when {
            topPressure < 100 || topPressure > 144 || lowPressure < 60 || lowPressure > 99 ||
                    pulse < 45 || pulse > 99 -> ListItemColors[4]

            topPressure in (100..104) || topPressure in (140..144) ||
                    lowPressure in (60..64) || lowPressure in (95..99) ||
                    pulse in (45..49) || pulse in (95..99) -> ListItemColors[3]

            topPressure in (105..109) || topPressure in (135..139) ||
                    lowPressure in (65..69) || lowPressure in (90..94) ||
                    pulse in (50..54) || pulse in (90..94) -> ListItemColors[2]

            topPressure in (110..114) || topPressure in (130..134) ||
                    lowPressure in (70..74) || lowPressure in (85..89) ||
                    pulse in (55..59) || pulse in (80..89) -> ListItemColors[1]

            else -> ListItemColors[0]
        }
    }

@Composable
fun ListItem(heartData: HeartData) {
    HorizontalDivider(thickness = 1.dp, color = Color.DarkGray)
    Row(

    ) {
        Text(
            text = heartData.time,
        )
        Text(text = heartData.topPressure.toString())
    }
}

@Composable
fun DataList(list: List<HeartData>) {
    LazyColumn {
        items(list) { listItem ->
            Column(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White,
                                listItem.getColor(), listItem.getColor(),
                                Color.White
                            )
                        ),
                    )
            ) {
                ListItem(heartData = listItem)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DataListPreview() {
    DataList(list = preview_list)
}