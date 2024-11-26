package com.example.drwebtest.repository

import android.graphics.drawable.Drawable

data class AppListItemData(
    val name: String,
    val packageName: String = "",
    val icon: Drawable
)

data class AppDetail(
    val name: String,
    val packageName: String,
    val version: String,
    val icon: Drawable,
    val checksum: String
)
