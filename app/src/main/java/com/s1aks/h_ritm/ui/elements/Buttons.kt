package com.s1aks.h_ritm.ui.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun AddIconButton(
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = Modifier.border(
            width = 1.dp,
            color = LocalContentColor.current.copy(),
            shape = CircleShape
        ),
        onClick = { onClick() }
    ) {
        Icon(imageVector = Icons.Default.Add, "Добавить")
    }
}

@Preview(showBackground = true)
@Composable
fun DoneIconButton(
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = Modifier.border(
            width = 1.dp,
            color = if (enabled)
                LocalContentColor.current.copy()
            else
                MaterialTheme.colorScheme.error,
            shape = CircleShape
        ),
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Icon(imageVector = Icons.Default.Done, "Подтвердить")
    }
}

@Preview(showBackground = true)
@Composable
fun PrevIconButton(
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    IconButton(
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Подтвердить",
            tint = if (enabled) Color.Green else Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NextIconButton(
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    IconButton(
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Подтвердить",
            tint = if (enabled) Color.Green else Color.Gray
        )
    }
}