package com.example.drwebtest.repository

import android.content.pm.PackageManager
import com.example.drwebtest.utils.ChecksumUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val packageManager: PackageManager,
    private val checksumUtils: ChecksumUtils
) : IAppRepository {
    override suspend fun getInstalledApps(): List<AppListItemData> {
        return withContext(Dispatchers.IO) {
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA).sortedBy { it.loadLabel(packageManager).toString() }.map { appInfo ->
                AppListItemData(
                    name = appInfo.loadLabel(packageManager).toString(),
                    packageName = appInfo.packageName,
                    icon = appInfo.loadIcon(packageManager)
                )
            }
        }
    }

    override suspend fun getAppDetails(packageName: String): AppDetail {
        return withContext(Dispatchers.IO) {
            val appInfo = safeCall(
                action = { packageManager.getApplicationInfo(packageName, 0) },
                onError = { throw IllegalArgumentException("Package not found: $packageName", it) }
            )

            val label = appInfo.loadLabel(packageManager).toString()

            val version = safeCall(
                action = {
                    packageManager.getPackageInfo(packageName, 0).versionName ?: "Unknown"
                },
                onError = { "Unknown" }
            )

            val icon = appInfo.loadIcon(packageManager)

            val apkFile = File(appInfo.sourceDir)
            if (!apkFile.exists()) {
                throw FileNotFoundException("APK file not found: ${apkFile.path}")
            }

            val checksum = safeCall(
                action = { checksumUtils.calculateSHA1(apkFile.path) },
                onError = { throw RuntimeException("Failed to calculate SHA1 for ${apkFile.path}", it) }
            )

            AppDetail(label, packageName, version, icon, checksum)
        }
    }

    private inline fun <T> safeCall(action: () -> T, onError: (Throwable) -> T): T {
        return try {
            action()
        } catch (e: Throwable) {
            onError(e)
        }
    }
}


