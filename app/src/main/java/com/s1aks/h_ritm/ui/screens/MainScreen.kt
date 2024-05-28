package com.s1aks.h_ritm.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.s1aks.h_ritm.ui.elements.AppBar
import com.s1aks.h_ritm.ui.screens.datalist.DataListViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreen(dataListViewModel: DataListViewModel = viewModel()) {
    val navController = rememberNavController()
    var screenState by remember { mutableStateOf(MainScreenState()) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(title = screenState.title) { screenState.actions?.invoke(this) }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.DataList.route,
                route = NavRoutes.MainRoute.name
            ) {
                mainGraph(navController) { screenState = it }
            }
        }
    }
}

data class MainScreenState(
    val title: @Composable () -> Unit = { Text("") },
    val actions: (@Composable RowScope.() -> Unit)? = null
)