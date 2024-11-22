package com.example.drwebtest.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drwebtest.MainActivityViewModel

@Composable
fun AppListScreen(
    navController: NavController,
    viewModel: MainActivityViewModel,
    innerPadding: PaddingValues
) {
    val apps = viewModel.apps.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        if (isLoading) {
            items(10) {
                AppSkeleton()
                HorizontalDivider()
            }
        } else {
            items(apps.value) { app ->
                AppListItem(
                    app = app,
                    onClick = {
                        viewModel.selectApp(app.packageName)
                        navController.navigate(AppRoutes.APP_DETAIL)
                    }
                )
                HorizontalDivider()
            }
        }
    }
}
