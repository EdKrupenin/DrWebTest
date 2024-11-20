package com.example.drwebtest.repository

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val packageManager: PackageManager
) : IAppRepository  {
    override suspend fun getInstalledApps(): List<String> {
        return withContext(Dispatchers.IO) { // Выполняем операцию в IO-потоке
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                .map { app -> app.loadLabel(packageManager).toString() }
        }
    }
}