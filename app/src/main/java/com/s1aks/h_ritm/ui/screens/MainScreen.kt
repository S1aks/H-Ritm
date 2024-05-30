package com.s1aks.h_ritm.ui.screens

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var screenState by remember { mutableStateOf(MainScreenState()) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val activity = (LocalContext.current as? Activity)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (screenState.drawerEnabled) {
                ModalDrawerSheet {
                    DrawerContent(
                        navController = navController,
                        scope = scope,
                        drawerState = drawerState,
                        onExit = { activity?.finish() }
                    )
                }
            }
        },
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppBar(
                    title = screenState.title,
                    navigationIconVisible = screenState.drawerEnabled,
                    onNavigationIconClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                ) { screenState.actions?.invoke(this) }
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
}

data class MainScreenState(
    val title: @Composable () -> Unit = { Text("") },
    val drawerEnabled: Boolean = true,
    val actions: (@Composable RowScope.() -> Unit)? = null
)