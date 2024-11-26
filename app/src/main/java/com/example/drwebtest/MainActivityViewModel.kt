package com.example.drwebtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drwebtest.repository.AppDetail
import com.example.drwebtest.repository.AppListItemData
import com.example.drwebtest.repository.IAppRepository
import com.example.drwebtest.utils.IAppChangeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val appRepository: IAppRepository,
    private val appChangeManager: IAppChangeManager,
) : ViewModel() {

    private val _apps = MutableStateFlow<List<AppListItemData>>(emptyList())
    val apps: StateFlow<List<AppListItemData>> = _apps

    private val _selectedApp = MutableStateFlow<AppDetail?>(null)
    val selectedApp: StateFlow<AppDetail?> = _selectedApp

    private val _launchAppEvent = MutableStateFlow("")
    val launchAppEvent: StateFlow<String> = _launchAppEvent

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        appChangeManager.setListener {
            loadApps()
        }
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            _isLoading.value = true
            _apps.value = appRepository.getInstalledApps()
            _isLoading.value = false
        }
    }

    fun selectApp(packageName: String) {
        viewModelScope.launch {
            _selectedApp.value = appRepository.getAppDetails(packageName)
        }
    }

    fun onLaunchAppClicked(packageName: String) {
        val result = appChangeManager.launchApp(packageName)
        result.onFailure { error ->
            _launchAppEvent.value = error.message.toString()
        }
    }

    fun clearLaunchAppEvent() {
        _launchAppEvent.value = ""
    }
}
