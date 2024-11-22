package com.example.drwebtest.utils

import android.content.Context

class AppLauncher (private val context: Context) : IAppLauncher {
    override fun launchApp(packageName: String): Result<Unit> {
        return try {
            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
                ?: return Result.failure(Exception("Cannot open this app"))
            context.startActivity(intent)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
