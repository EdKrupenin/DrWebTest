package com.example.drwebtest.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.drwebtest.MainActivityViewModel
import com.example.drwebtest.repository.AppDetail

@Composable
fun AppDetailScreen(
    innerPadding: PaddingValues,
    viewModel: MainActivityViewModel
) {
    val context = LocalContext.current
    val selectedApp = viewModel.selectedApp.collectAsState()
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        Text(
            text = "" + selectedApp.value?.let { appDetailScreenText(it) },
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            try {
                val intent = selectedApp.value?.packageName?.let {
                    context.packageManager.getLaunchIntentForPackage(
                        it
                    )
                }
                if (intent != null) {
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Cannot open this app", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Open App")
        }
    }
}

fun appDetailScreenText(selectedApp: AppDetail): String {
    return "Label: ${selectedApp.label}\nPackage Name: ${selectedApp.packageName}\n" +
            "Version: ${selectedApp.version}\nChecksum: ${selectedApp.apkChecksum}"
}