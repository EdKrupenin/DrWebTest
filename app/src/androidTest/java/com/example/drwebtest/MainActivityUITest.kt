package com.example.drwebtest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.drwebtest.di.AppModule
import com.example.drwebtest.repository.AppDetail
import com.example.drwebtest.repository.AppListItemData
import com.example.drwebtest.repository.IAppRepository
import com.example.drwebtest.ui.screen.AppListScreen
import com.example.drwebtest.ui.theme.DrWebTestTheme
import com.example.drwebtest.utils.IAppChangeManager
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AppListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        hiltRule.inject()
        val context = ApplicationProvider.getApplicationContext<Context>()

    }

    @Test
    fun appListScreen_showsMockedApps() {
        composeTestRule.setContent {
            DrWebTestTheme {
                AppListScreen(
                    navController = rememberNavController(),
                    viewModel = hiltViewModel(),
                    innerPadding = PaddingValues()
                )
            }
        }

        composeTestRule.onNodeWithText("Test App 1").assertExists()
        composeTestRule.onNodeWithText("Test App 2").assertExists()
    }
}


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object FakeAppModule {

    @Provides
    @Singleton
    fun provideFakeAppRepository(): IAppRepository {
        return FakeAppRepository(
            listOf(
                AppListItemData(
                    name = "Test App 1",
                    packageName = "com.example.app1",
                    icon = mockk<Drawable>()
                ),
                AppListItemData(
                    name = "Test App 2",
                    packageName = "com.example.app2",
                    icon = mockk<Drawable>()
                )
            )
        )
    }

    @Provides
    @Singleton
    fun provideFakeAppLauncher(@ApplicationContext context: Context): IAppChangeManager {
        return FakeAppLauncher(context)
    }

}

class FakeAppRepository(private val apps: List<AppListItemData>) : IAppRepository {
    override suspend fun getInstalledApps(): List<AppListItemData> = apps

    override suspend fun getAppDetails(packageName: String): AppDetail {
        return apps.firstOrNull { it.packageName == packageName }
            ?.let { AppDetail(it.name, it.packageName, "1.0", it.icon, "checksum") }
            ?: throw IllegalArgumentException("App not found")
    }
}

class FakeAppLauncher (private val context: Context) : IAppChangeManager {
    private var listener: (() -> Unit)? = null
    private val appChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (action == Intent.ACTION_PACKAGE_ADDED || action == Intent.ACTION_PACKAGE_REMOVED) {
                listener?.invoke()
            }
        }
    }

    override fun startListening() {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addDataScheme("package")
        }
        context.registerReceiver(appChangeReceiver, filter)
    }

    override fun stopListening() {
        context.unregisterReceiver(appChangeReceiver)
    }

    override fun setListener(listener: () -> Unit) {
        this.listener = listener
    }

    override fun launchApp(packageName: String): Result<Unit> {
        return try {
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

@CustomTestApplication(BaseApplication::class)
interface HiltTestApplication