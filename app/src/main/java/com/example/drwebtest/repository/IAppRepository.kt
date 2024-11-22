package com.example.drwebtest.repository

interface IAppRepository {
    suspend fun getInstalledApps(): List<AppListItemData>
    suspend fun getAppDetails(packageName: String): AppDetail
}