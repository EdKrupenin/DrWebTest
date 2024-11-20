package com.example.drwebtest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drwebtest.repository.IAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val appRepository: IAppRepository
) : ViewModel() {

    private val _apps = MutableStateFlow<List<String>>(emptyList())
    val apps: StateFlow<List<String>> = _apps

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            _apps.value = appRepository.getInstalledApps()
        }
    }
}
