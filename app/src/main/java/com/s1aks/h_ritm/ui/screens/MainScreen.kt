package com.s1aks.h_ritm.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.ui.screens.datalist.DataListScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
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
    val closeSheet = {
        scope.launch {
            topPressure = ""
            lowPressure = ""
            pulse = ""
            editedData = null
            sheetState.hide()
        }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showBottomSheet = false
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("H-Ritm")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet = true }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxSize(0.95f),
                    onDismissRequest = { closeSheet.invoke() },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp)
                    ) {
                        Text(
                            fontSize = 20.sp,
                            text = "Верхнее давление"
                        )
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
                            keyboardActions = KeyboardActions(
                                onNext = { secondRef.requestFocus() }
                            ),
                        )
                        Text(
                            fontSize = 20.sp,
                            text = "Нижнее давление"
                        )
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
                            keyboardActions = KeyboardActions(
                                onNext = { thirdRef.requestFocus() }
                            ),
                        )
                        Text(
                            fontSize = 20.sp,
                            text = "Пульс"
                        )
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
                            keyboardActions = KeyboardActions(
                                onDone = { keyboardController?.hide() }
                            ),
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
                                onClick = { closeSheet.invoke() }
                            ) {
                                Text(
                                    fontSize = 20.sp,
                                    text = "Сохранить"
                                )
                            }
                            Button(
                                onClick = { closeSheet.invoke() }
                            ) {
                                Text(
                                    fontSize = 20.sp,
                                    text = "Отмена"
                                )
                            }
                        }
                    }
                    LaunchedEffect(Unit) {
                        firstRef.requestFocus()
                    }
                }
            }
            DataListScreen()
        }
    }
}

