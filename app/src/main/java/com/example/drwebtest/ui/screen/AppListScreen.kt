package com.example.drwebtest.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.drwebtest.ui.AppListViewModel

@Composable
fun AppListScreen(viewModel: AppListViewModel = hiltViewModel(), innerPadding: PaddingValues) {
    val apps = viewModel.apps.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        items(apps.value) { app ->
            Text(text = app, modifier = Modifier.padding(8.dp))
        }
    }
}