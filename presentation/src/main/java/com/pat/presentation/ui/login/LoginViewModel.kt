package com.pat.presentation.ui.login

import android.graphics.Bitmap
import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.orhanobut.logger.Logger
import com.pat.domain.model.exception.NeedUserRegistrationException
import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.CreatePatInfoDetail
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.domain.model.place.PlaceSearchRequestInfo
import com.pat.domain.usecase.auth.GetUserCodeUseCase
import com.pat.domain.usecase.auth.LoginUseCase
import com.pat.domain.usecase.auth.RegisterUserUseCase
import com.pat.domain.usecase.image.GetByteArrayByUriUseCase
import com.pat.domain.usecase.pat.CreatePatUseCase
import com.pat.domain.usecase.place.GetSearchCoordinateUseCase
import com.pat.domain.usecase.place.GetSearchPlaceUseCase
import com.pat.presentation.model.PatBitmap
import com.pat.presentation.util.image.byteArrayToBitmap
import com.pat.presentation.util.image.getCompressedBytes
import com.pat.presentation.util.image.getRotatedBitmap
import com.pat.presentation.util.image.getScaledBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KProperty0

sealed class Event {
    object LoginSuccess : Event()
    object LoginFailed : Event()

    object UserRegistrationRequired : Event()

    object RegistrationSuccess : Event()
    object RegistrationFailed :Event()
}
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val getUserCodeUseCase: GetUserCodeUseCase,
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()
    fun onLoginWithKakao() {
        viewModelScope.launch {
            val result = loginUseCase()
            if (result.isSuccess) {
                _event.emit(Event.LoginSuccess)
                Logger.t("login").i("로그인성공")
            } else {
                val error = result.exceptionOrNull()
                if (error is NeedUserRegistrationException) {
                    var userCode : String = ""
                    getUserCodeUseCase().onSuccess { userCode = it.toString() }
                    val registerResult = registerUserUseCase(userCode)
                    if (registerResult.isSuccess) {
                        Logger.t("login").i("회원가입성공")
                        //회원가입성공
                        _event.emit(Event.RegistrationSuccess)
                    } else {
                        Logger.t("login").i("회원가입실패")

                        _event.emit(Event.RegistrationFailed)
                    }
                } else {
                    Logger.t("login").i("로그인실패")

                    _event.emit(Event.LoginFailed)
                }
            }
        }
    }

}