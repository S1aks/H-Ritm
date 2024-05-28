package com.s1aks.h_ritm.ui.screens.dataedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.ui.elements.DoneIconButton
import com.s1aks.h_ritm.ui.screens.MainScreenState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DataEditScreen(
    navController: NavController,
    onComposing: (MainScreenState) -> Unit,
    viewModel: DataEditViewModel = viewModel(),
    id: Int
) {
    val new = id == 0
    val (firstRef, secondRef, thirdRef) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var topPressure by remember { mutableStateOf("") }
    var lowPressure by remember { mutableStateOf("") }
    var pulse by remember { mutableStateOf("") }
    val allFieldsOk = fun(): Boolean = topPressure.isNotEmpty() && topPressure.toInt() > 25
            && lowPressure.isNotEmpty() && lowPressure.toInt() > 25
            && if (pulse.isNotEmpty()) {
        pulse.toInt() > 20
    } else true
    val data by viewModel.data.collectAsState()
    LaunchedEffect(data) {
        data?.let {
            topPressure = it.topPressure.toString()
            lowPressure = it.lowPressure.toString()
            pulse = if (it.pulse != null) {
                it.pulse.toString()
            } else {
                ""
            }
        }
    }
    var saveData by remember { mutableStateOf<HeartData?>(null) }
    saveData = if (allFieldsOk()) {
        HeartData(
            if (new) {
                0
            } else {
                id
            },
            System.currentTimeMillis(),
            topPressure.toInt(),
            lowPressure.toInt(),
            if (pulse.isNotEmpty() && pulse.toInt() > 20) {
                pulse.toInt()
            } else null
        )
    } else null
    LaunchedEffect(Unit) {
        onComposing(
            MainScreenState(
                title = { Text(if (new) "Добавить показания" else "Редактировать показания") },
                actions = {
                    DoneIconButton(enabled = allFieldsOk()) {
                        saveData?.let {
                            if (new) {
                                viewModel.insertData(it)
                                navController.popBackStack()
                            } else {
                                viewModel.updateData(it)
                                navController.popBackStack()
                            }
                        }
                    }
                }
            )
        )
        if (!new) {
            viewModel.getData(id)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp)
    ) {
        Text(fontSize = 20.sp, text = "Верхнее давление")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp, bottom = 10.dp)
                .focusRequester(firstRef),
            value = topPressure,
            onValueChange = { topPressure = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { secondRef.requestFocus() }),
        )
        Text(fontSize = 20.sp, text = "Нижнее давление")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp, bottom = 10.dp)
                .focusRequester(secondRef),
            value = lowPressure,
            onValueChange = { lowPressure = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { thirdRef.requestFocus() }),
        )
        Text(fontSize = 20.sp, text = "Пульс")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp, bottom = 10.dp)
                .focusRequester(thirdRef),
            value = pulse,
            onValueChange = { pulse = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        )
    }
    LaunchedEffect(Unit) {
        if (new) {
            firstRef.requestFocus()
        }
    }
}