package com.s1aks.h_ritm.ui.screens.data_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.ui.elements.ContextMenuItem
import com.s1aks.h_ritm.utils.getColor
import com.s1aks.h_ritm.utils.getTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DataListItem(
    heartData: HeartData,
    contextMenu: List<ContextMenuItem> = listOf()
) {
    var isContextMenuVisible by rememberSaveable { mutableStateOf(false) }
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(true) {
                detectTapGestures(onLongPress = {
                    isContextMenuVisible = true
                    pressOffset = DpOffset(x = it.x.dp, y = it.y.dp)
                })
            }
            .background(
                brush = Brush.linearGradient(
                    colors = if (!isContextMenuVisible) listOf(
                        Color.Transparent,
                        heartData.getColor(),
                        heartData.getColor(),
                        Color.Transparent
                    ) else listOf(Color.Blue, Color.Transparent, Color.Blue)
                ),
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontSize = 20.sp,
                text = heartData.dateTime.getTime,
            )
            Text(
                fontSize = 30.sp,
                text = "${heartData.topPressure} / ${heartData.lowPressure}"
            )
            Text(
                modifier = Modifier.width(60.dp),
                fontSize = 24.sp,
                text = "♡ ${heartData.pulse ?: " -"}"
            )
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
            offset = DpOffset(
                x = pressOffset.x / 3,
                y = (-20).dp
            )
        ) {
            contextMenu.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            fontSize = 18.sp,
                            text = item.label
                        )
                    },
                    onClick = {
                        item.action(heartData.id)
                        isContextMenuVisible = false
                    })
            }
        }
    }
    HorizontalDivider(color = Color.DarkGray)
}
