package com.example.drwebtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.drwebtest.ui.screen.AppNavigation
import com.example.drwebtest.ui.theme.DrWebTestTheme
import com.example.drwebtest.utils.IAppChangeManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appChangeManager: IAppChangeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appChangeManager.startListening()
        enableEdgeToEdge()
        setContent {
            DrWebTestTheme {
                AppNavigation()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appChangeManager.stopListening()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DrWebTestTheme {
        //AppListScreen()
    }
}