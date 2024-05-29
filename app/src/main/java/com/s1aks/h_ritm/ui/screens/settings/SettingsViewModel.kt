package com.s1aks.h_ritm.ui.screens.settings

import androidx.lifecycle.ViewModel
import com.s1aks.h_ritm.data.RepositoryImpl
import com.s1aks.h_ritm.domain.Repository

class SettingsViewModel(
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {

}
