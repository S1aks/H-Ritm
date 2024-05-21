package com.s1aks.h_ritm.ui.screens.dataedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.s1aks.h_ritm.data.entities.HeartData

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DataEditScreen(
    dataEditViewModel: DataEditViewModel = viewModel()
) {
    val (firstRef, secondRef, thirdRef) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var editedData by remember { mutableStateOf<HeartData?>(null) }
    var topPressure by remember { mutableStateOf("") }
    var lowPressure by remember { mutableStateOf("") }
    var pulse by remember { mutableStateOf("") }
    val sheetFieldsOk = topPressure.isNotEmpty() && topPressure.toInt() > 25
            && lowPressure.isNotEmpty() && lowPressure.toInt() > 25
            && if (pulse.isNotEmpty()) {
        pulse.toInt() > 20
    } else true
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                enabled = sheetFieldsOk,
                onClick = {
                    dataEditViewModel.insertData(
                        HeartData(
                            0,
                            System.currentTimeMillis(),
                            topPressure.toInt(),
                            lowPressure.toInt(),
                            if (pulse.isNotEmpty() && pulse.toInt() > 20) {
                                pulse.toInt()
                            } else null
                        )
                    )
                    // TODO: Close EditScreen
                }
            ) { Text(fontSize = 20.sp, text = "Сохранить") }
            Button(
                onClick = {
                    // TODO: Close EditScreen
                }
            ) { Text(fontSize = 20.sp, text = "Отмена") }
        }
    }
    LaunchedEffect(Unit) {
        firstRef.requestFocus()
    }
}