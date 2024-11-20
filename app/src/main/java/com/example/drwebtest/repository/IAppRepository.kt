package com.example.drwebtest.repository

interface IAppRepository {
    suspend fun getInstalledApps(): List<String>
}