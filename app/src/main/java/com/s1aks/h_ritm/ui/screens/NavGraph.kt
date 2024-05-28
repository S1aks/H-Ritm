package com.s1aks.h_ritm.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.s1aks.h_ritm.ui.screens.dataedit.DataEditScreen
import com.s1aks.h_ritm.ui.screens.datalist.DataListScreen

fun NavGraphBuilder.idComposable(
    route: String,
    content: @Composable (id: Int) -> Unit
) {
    composable(
        route = route,
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        content(backStackEntry.arguments?.getString("id")?.toInt() ?: 0)
    }
}

enum class NavRoutes {
    MainRoute
}

sealed class Screen(val route: String) {
    object DataList : Screen("data_list")
    class DataEdit(id: String = "{id}") : Screen("data_edit/$id")
}

fun NavController.clearAndNavigate(route: String) {
    popBackStack(graph.startDestinationId, true)
    graph.setStartDestination(route)
    navigate(route)
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    onComposing: (MainScreenState) -> Unit
) {
    composable(Screen.DataList.route) {
        DataListScreen(navController = navController, onComposing = onComposing)
    }
    idComposable(Screen.DataEdit().route) { id ->
        DataEditScreen(navController = navController, onComposing = onComposing, id = id)
    }
}
