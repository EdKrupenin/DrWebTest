package com.example.drwebtest.repository

import android.graphics.Bitmap

data class AppListItemData(
    val name: String,
    val packageName: String = "",
    val icon: Bitmap
)

data class AppDetail(
    val name: String,
    val packageName: String,
    val version: String,
    val icon: Bitmap,
    val checksum: String
)
