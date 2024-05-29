package com.s1aks.h_ritm.ui.screens.data_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s1aks.h_ritm.utils.listItemColors

@Composable
fun DataListDateItem(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        listItemColors[0],
                        Color.Transparent
                    )
                )
            )
            .padding(horizontal = 12.dp, vertical = 2.dp),
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic,
        text = text
    )
    HorizontalDivider(color = Color.DarkGray)
}