package com.example.drwebtest.repository

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val packageManager: PackageManager
) : IAppRepository {
    override suspend fun getInstalledApps(): List<ApplicationInfo> {
        return withContext(Dispatchers.IO) {
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        }
    }

    override suspend fun parseApplicationInfo(appInfo: ApplicationInfo): AppDetail {
        return withContext(Dispatchers.IO) {
            val label = appInfo.loadLabel(packageManager).toString()
            val packageName = appInfo.packageName
            val version = try {
                packageManager.getPackageInfo(appInfo.packageName, 0).versionName ?: "Unknown"
            } catch (e: Exception) {
                "Unknown"
            }
            val apkFile = File(appInfo.sourceDir)
            val checksum = "" // TODO: C++ libs for checksum calculation

            AppDetail(label, packageName, version, checksum)
        }
    }
}