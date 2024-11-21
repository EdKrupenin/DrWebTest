package com.example.drwebtest.repository

import android.content.pm.ApplicationInfo

interface IAppRepository {
    suspend fun getInstalledApps(): List<ApplicationInfo>
    suspend fun parseApplicationInfo(appInfo: ApplicationInfo): AppDetail
}