package com.example.drwebtest.utils

interface IAppLauncher {
    fun launchApp(packageName: String): Result<Unit>
}