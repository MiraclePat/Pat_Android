package com.pat.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    private var _splashEvent = MutableSharedFlow<Boolean>()
    val splashEvent = _splashEvent.asSharedFlow()

    init {
        splashDelay()
    }

    private fun splashDelay() {
        viewModelScope.launch {
            delay(1000)
            _splashEvent.emit(true)
        }
    }
}