package com.s1aks.h_ritm.ui.screens.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.s1aks.h_ritm.ui.elements.DoneIconButton
import com.s1aks.h_ritm.ui.screens.MainScreenState

@Composable
fun SettingsScreen(
    navController: NavController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: SettingsViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = { Text("Настройки") },
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = true) { //todo
                        //viewModel.updateData(it)
                        navController.popBackStack()

                    }
                }
            )
        )
        //viewModel.getAllData()
    }

}