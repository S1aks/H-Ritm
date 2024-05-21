package com.s1aks.h_ritm.ui.screens.datalist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.ui.elements.ContextMenuItem
import com.s1aks.h_ritm.utils.getDate
import com.s1aks.h_ritm.utils.preview_list

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DataListScreen(dataListViewModel: DataListViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        dataListViewModel.getAllData()
    }
    val screenState: DataListScreenState by remember {
        mutableStateOf(DataListScreenState(
            data = listOf(),
            contextMenu = listOf(
                ContextMenuItem("Редактировать") { id ->
                    // TODO: To EditScreen
                },
                ContextMenuItem("Удалить") { id ->
                    dataListViewModel.deleteData(id)
                }
            )
        ))
    }
    val dataList by dataListViewModel.data.collectAsState()
    if (dataList.isNotEmpty()) {
        DataList(state = screenState.copy(data = dataList))
    } else {
        Text(text = "Пусто")
    }
}

data class DataListScreenState(
    var data: List<HeartData>,
    val contextMenu: List<ContextMenuItem>
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DataList(state: DataListScreenState) {
    Column {
        HorizontalDivider(color = Color.DarkGray)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.data) { listItem ->
                val indexItem = state.data.indexOf(listItem)
                val currentDate = listItem.dateTime.getDate
                val prevDate = if (indexItem > 0) {
                    state.data[indexItem - 1].dateTime.getDate
                } else currentDate
                if (indexItem == 0 || currentDate != prevDate) DataListDateItem(currentDate)
                DataListItem(heartData = listItem, contextMenu = state.contextMenu)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DataListScreenPreview() {
    DataList(state = DataListScreenState(preview_list, listOf()))
}
