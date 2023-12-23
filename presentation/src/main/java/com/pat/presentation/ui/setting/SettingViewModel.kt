package com.pat.presentation.ui.setting

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pat.domain.model.member.MyProfileContent
import com.pat.domain.usecase.member.GetMyProfileUseCase
import com.pat.presentation.util.resultException
import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.CreatePatInfoDetail
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.domain.model.place.PlaceSearchRequestInfo
import com.pat.domain.usecase.auth.GetUserCodeUseCase
import com.pat.domain.usecase.auth.LoginUseCase
import com.pat.domain.usecase.auth.RegisterUserUseCase
import com.pat.domain.usecase.image.GetByteArrayByUriUseCase
import com.pat.domain.usecase.member.DeleteMemberUseCase
import com.pat.domain.usecase.member.GetMyProfileUseCase
import com.pat.domain.usecase.pat.CreatePatUseCase
import com.pat.domain.usecase.place.GetSearchCoordinateUseCase
import com.pat.domain.usecase.place.GetSearchPlaceUseCase
import com.pat.presentation.model.PatBitmap
import com.pat.presentation.ui.pat.ParticipateEvent
import com.pat.presentation.ui.pat.PatDetailUiState
import com.pat.presentation.ui.pat.PatUpdateUiState
import com.pat.presentation.util.image.byteArrayToBitmap
import com.pat.presentation.util.image.getCompressedBytes
import com.pat.presentation.util.image.getRotatedBitmap
import com.pat.presentation.util.image.getScaledBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KProperty0

sealed class SettingEvent {
    object GetMyProfileSuccess : SettingEvent()
    object GetMyProfileFailed : SettingEvent()

    object DeleteUserSuccess : SettingEvent()
    object DeleteUserFailed : SettingEvent()

}
data class SettingUiState(
    val profileContent: MyProfileContent? = null
)

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<SettingEvent>()
    val event = _event.asSharedFlow()

    init {
        getMyProfile()
    }

    fun getMyProfile() {
        viewModelScope.launch {
            val result = getMyProfileUseCase()
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _event.emit(SettingEvent.GetMyProfileSuccess)
                _uiState.emit(SettingUiState(profileContent = content))

            } else {
                _event.emit(SettingEvent.GetMyProfileFailed)
                val error = result.exceptionOrNull()
                resultException(error)
            }
        }
    }
    
    fun deleteUser(){
        viewModelScope.launch {
            val result = deleteMemberUseCase()
            if (result.isSuccess) {
                _event.emit(SettingEvent.DeleteUserSuccess)
                Log.e("custom", "탈퇴성공")
            } else {
                _event.emit(SettingEvent.DeleteUserFailed)
                Log.e("custom", "탈퇴 실패")
            }
        }
    }
}