package com.s1aks.h_ritm.ui.screens.datalist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.ui.MainViewModel

@Composable
fun DataListScreen(viewModel: MainViewModel) {
    DataList(list = viewModel.dataList)
}

@Composable
fun DataList(list: List<HeartData>) {
    LazyColumn {
        items(list) { listItem ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
                    .clickable {

                    },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 4.dp
                    )
                ) {
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = listItem.time,
                    )
                    Text(text = listItem.topPressure.toString())
                }
            }
        }
    }
}

private val preview_list = listOf(
    HeartData(1, "2024-09-01", 120, 80, 50),
    HeartData(2, "2024-09-02", 150, 90, 74),
    HeartData(3, "2024-09-03", 110, 70, 47),
    HeartData(4, "2024-09-04", 125, 82, 54),
)

@Preview
@Composable
fun PreviewDataList() {
    DataList(list = preview_list)
}