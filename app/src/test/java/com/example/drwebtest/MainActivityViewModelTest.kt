package com.example.drwebtest

import com.example.drwebtest.repository.AppDetail
import com.example.drwebtest.repository.AppListItemData
import com.example.drwebtest.repository.IAppRepository
import com.example.drwebtest.utils.IAppLauncher
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val appRepository = mockk<IAppRepository>()
    private val appLauncher = mockk<IAppLauncher>()
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mockApps : List<AppListItemData>

    @Before
    fun setup() {
        mockApps = listOf(
            AppListItemData(name = "App1", packageName = "com.example.app1", icon = mockk()),
            AppListItemData(name = "App2", packageName = "com.example.app2", icon = mockk())
        )
        coEvery { appRepository.getInstalledApps() } returns mockApps
        viewModel = MainActivityViewModel(appRepository, appLauncher)
    }

    @Test
    fun `loadApps updates apps and loading state`() = runTest {

        viewModel
        advanceUntilIdle()

        assertEquals(false, viewModel.isLoading.value)
        assertEquals(mockApps, viewModel.apps.value)
    }

    @Test
    fun `selectApp updates selectedApp state`() = runTest {
        val mockAppDetail = AppDetail(
            name = "App1",
            packageName = "com.example.app1",
            version = "1.0",
            icon = mockk(),
            checksum = "123456"
        )
        coEvery { appRepository.getAppDetails("com.example.app1") } returns mockAppDetail

        viewModel.selectApp("com.example.app1")

        advanceUntilIdle()
        assertEquals(mockAppDetail, viewModel.selectedApp.value)
    }

    @Test
    fun `onLaunchAppClicked updates launchAppEvent on failure`() {
        every { appLauncher.launchApp("com.example.app1") } returns Result.failure(Exception("Cannot launch app"))

        viewModel.onLaunchAppClicked("com.example.app1")

        assertEquals("Cannot launch app", viewModel.launchAppEvent.value)
    }

    @Test
    fun `clearLaunchAppEvent resets launchAppEvent state`() {
        viewModel.clearLaunchAppEvent()

        assertEquals("", viewModel.launchAppEvent.value)
    }
}

@ExperimentalCoroutinesApi
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
