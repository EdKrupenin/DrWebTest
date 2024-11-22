package com.example.drwebtest.repository

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.example.drwebtest.utils.ChecksumUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
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
                    icon = (appInfo.loadIcon(packageManager) as Drawable).toBitmap()
                )
            }
        }
    }

    override suspend fun getAppDetails(packageName: String): AppDetail {
        return withContext(Dispatchers.IO) {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            val label = appInfo.loadLabel(packageManager).toString()
            val version = try {
                packageManager.getPackageInfo(packageName, 0).versionName ?: "Unknown"
            } catch (e: Exception) {
                "Unknown"
            }
            val icon = (appInfo.loadIcon(packageManager) as Drawable).toBitmap()
            val apkFile = File(appInfo.sourceDir)
            val checksum = checksumUtils.calculateSHA1(apkFile.path)

            AppDetail(label, packageName, version, icon, checksum)
        }
    }
}