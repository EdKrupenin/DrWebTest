package com.example.drwebtest

import android.content.pm.ApplicationInfo
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drwebtest.repository.AppDetail
import com.example.drwebtest.repository.IAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val appRepository: IAppRepository
) : ViewModel() {

    private val _apps = MutableStateFlow<List<ApplicationInfo>>(emptyList())
    val apps: StateFlow<List<ApplicationInfo>> = _apps

    private val _selectedApp = MutableStateFlow<AppDetail?>(null)
    val selectedApp: StateFlow<AppDetail?> = _selectedApp

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            _apps.value = appRepository.getInstalledApps()
        }
    }

    fun selectApp(app: ApplicationInfo) {
        viewModelScope.launch {
            val appDetail = appRepository.parseApplicationInfo(app)
            Log.d("MainActivityViewModel", "Selected app: $app appDetail $appDetail")
            _selectedApp.value = appDetail
        }
    }
}
