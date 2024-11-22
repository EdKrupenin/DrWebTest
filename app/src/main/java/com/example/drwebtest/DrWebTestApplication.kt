package com.example.drwebtest

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DrWebTestApplication : BaseApplication()

open class BaseApplication : Application() {
    init {
        Log.d("HiltTest", "BaseApplication initialized")
    }
}

