package com.example.drwebtest.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppChangeManager @Inject constructor(
    private val context: Context
) : IAppChangeManager {
    private var listener: (() -> Unit)? = null
    private val appChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (action == Intent.ACTION_PACKAGE_ADDED || action == Intent.ACTION_PACKAGE_REMOVED) {
                listener?.invoke()
            }
        }
    }

    override fun startListening() {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addDataScheme("package")
        }
        context.registerReceiver(appChangeReceiver, filter)
    }

    override fun stopListening() {
        context.unregisterReceiver(appChangeReceiver)
    }

    override fun setListener(listener: () -> Unit) {
        this.listener = listener
    }

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
