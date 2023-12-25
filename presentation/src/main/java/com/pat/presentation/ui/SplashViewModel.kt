package com.pat.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pat.domain.usecase.auth.AutoLoginUseCase
import com.pat.domain.usecase.auth.GetUserKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashEvent {
    object LoginSuccess : SplashEvent()
    object LoginFailed : SplashEvent()
}
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val autoLoginUseCase : AutoLoginUseCase,
    private val getUserKeyUseCase : GetUserKeyUseCase,
) : ViewModel() {
    private var _splashEvent = MutableSharedFlow<SplashEvent>()
    val splashEvent = _splashEvent.asSharedFlow()

    init {
        autoLogin()
    }

    private fun autoLogin() {
        viewModelScope.launch {
            val result = getUserKeyUseCase()
            if (result.isSuccess) {
                if(result.getOrThrow().isNullOrEmpty()){
                    _splashEvent.emit(SplashEvent.LoginFailed)

                }else{
                    val loginResult = autoLoginUseCase(result.getOrThrow().toString())
                    if (loginResult.isSuccess) {
                        _splashEvent.emit(SplashEvent.LoginSuccess)
                        Log.e("custom", "자동로그인 성공")
                    } else {
                        _splashEvent.emit(SplashEvent.LoginFailed)
                        Log.e("custom", "자동로그인 실패")
                    }
                }
            }else{
                _splashEvent.emit(SplashEvent.LoginFailed)

            }
        }
    }
}