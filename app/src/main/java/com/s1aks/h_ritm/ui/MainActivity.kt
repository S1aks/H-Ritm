package com.s1aks.h_ritm.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import com.s1aks.h_ritm.ui.screens.MainScreen
import com.s1aks.h_ritm.ui.theme.HRitmTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            (LocalContext.current as? Activity)?.requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            HRitmTheme {
                MainScreen()
            }
        }
    }
}
