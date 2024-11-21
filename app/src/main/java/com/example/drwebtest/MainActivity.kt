package com.example.drwebtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drwebtest.di.SomeDependency
import com.example.drwebtest.ui.screen.AppListScreen
import com.example.drwebtest.ui.screen.AppNavigation
import com.example.drwebtest.ui.screen.TopBar
import com.example.drwebtest.ui.theme.DrWebTestTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var someDependency: SomeDependency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(someDependency.sayHello())
        enableEdgeToEdge()
        setContent {
            DrWebTestTheme {
                AppNavigation()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DrWebTestTheme {
        //AppListScreen()
    }
}