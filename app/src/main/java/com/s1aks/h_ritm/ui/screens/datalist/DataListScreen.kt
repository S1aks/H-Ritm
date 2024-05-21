package com.s1aks.h_ritm.ui.screens.datalist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.ui.MainViewModel
import com.s1aks.h_ritm.utils.getDate
import com.s1aks.h_ritm.utils.preview_list

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DataListScreen(mainViewModel: MainViewModel = viewModel()) {
    if (mainViewModel.dataList.isNotEmpty()) {
        DataList(list = mainViewModel.dataList)
    } else {
        DataList(list = preview_list.sortedByDescending { it.dateTime })
    }
    //Text(text = "Пусто")

//    contextMenu = listOf(
//        ContextMenuItem("Редактировать") { id ->
//            viewModel.clearStates()
//            navController.navigate(Screen.DirectionEdit(id.toString()).route)
//        },
//        ContextMenuItem("Удалить") { id ->
//            viewModel.deleteData(id)
//        }
//    )

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
                if (indexItem == 0 || currentDate != prevDate) DataListDateItem(currentDate)
                DataListItem(heartData = listItem)
            }
        }
    }
}
