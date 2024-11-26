package com.example.drwebtest.utils

interface IAppChangeManager {
    fun startListening()
    fun stopListening()
    fun setListener(listener: () -> Unit)
    fun launchApp(packageName: String): Result<Unit>
}
