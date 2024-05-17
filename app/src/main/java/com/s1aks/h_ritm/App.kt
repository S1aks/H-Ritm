package com.s1aks.h_ritm

import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}