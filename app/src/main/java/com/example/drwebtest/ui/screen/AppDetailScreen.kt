package com.example.drwebtest.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.drwebtest.MainActivityViewModel

@Composable
fun AppDetailScreen(
    innerPadding: PaddingValues,
    viewModel: MainActivityViewModel
) {
    val context = LocalContext.current
    val selectedApp by viewModel.selectedApp.collectAsState()
    val launchError by viewModel.launchAppEvent.collectAsState()

    if (launchError.isNotEmpty()) {
        Toast.makeText(context, launchError, Toast.LENGTH_SHORT).show()
        viewModel.clearLaunchAppEvent()
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        selectedApp?.let { appDetail ->
            Text(
                text = appDetail.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Image(
                bitmap = appDetail.icon.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
                    .padding(bottom = 16.dp)
            )
            AutoResizingTextField(
                value = appDetail.packageName,
                label = "Package Name"
            )
            AutoResizingTextField(
                value = appDetail.version,
                label = "Version"
            )
            AutoResizingTextField(
                value = appDetail.checksum,
                label = "Checksum"
            )
            Button(onClick = {
                viewModel.onLaunchAppClicked(appDetail.packageName)
            }) {
                Text("Open App")
            }
        }
    }
}

@Composable
fun AutoResizingTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        readOnly = true,
        onValueChange = { },
        label = {
            Text(
                text = label
            )
        },
        modifier = modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
    )
}