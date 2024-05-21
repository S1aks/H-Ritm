package com.s1aks.h_ritm.ui.elements

data class ContextMenuItem(
    val label: String,
    val action: (id: Int) -> Unit
)