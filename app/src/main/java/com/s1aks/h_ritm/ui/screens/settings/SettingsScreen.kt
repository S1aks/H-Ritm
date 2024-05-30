package com.s1aks.h_ritm.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.s1aks.h_ritm.data.entities.PrefData
import com.s1aks.h_ritm.ui.elements.DoneIconButton
import com.s1aks.h_ritm.ui.screens.MainScreenState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: SettingsViewModel = viewModel()
) {
    var age by remember { mutableStateOf("") }
    val allFieldsOk = fun(): Boolean = age.isNotEmpty() && age.toInt() in 0..130
    val (firstRef) = remember { FocusRequester.createRefs() }
    val data by viewModel.data.collectAsState()
    LaunchedEffect(data) {
        data?.let {
            if (it.age > 0) age = it.age.toString()
        }
    }
    var saveData by remember { mutableStateOf<PrefData?>(null) }
    saveData = if (allFieldsOk()) {
        PrefData(age.toInt())
    } else null
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = { Text("Настройки") },
                drawerEnabled = false,
                actions = {
                    DoneIconButton(enabled = allFieldsOk()) {
                        saveData?.let {
                            viewModel.savePrefData(it)
                            navController.popBackStack()
                        }
                    }
                }
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp)
    ) {
        if ((data == null) or (data?.age == 0)) {
            Text(
                fontSize = 18.sp,
                color = Color.Red,
                text = "Первый запуск приложения, введите пожалуйста возраст!"
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        Text(fontSize = 20.sp, text = "Возраст")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp, bottom = 10.dp)
                .focusRequester(firstRef),
            value = age,
            onValueChange = { age = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { }), //secondRef.requestFocus()
        )
    }
}
