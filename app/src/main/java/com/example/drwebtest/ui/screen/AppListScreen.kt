package com.example.drwebtest.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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

    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        items(apps.value) { app ->
            Text(
                text = app.loadLabel(navController.context.packageManager).toString(),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        viewModel.selectApp(app)
                        navController.navigate(AppRoutes.APP_DETAIL)
                    }
            )
        }
    }
}