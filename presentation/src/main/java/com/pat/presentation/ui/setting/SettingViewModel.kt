package com.pat.presentation.ui.setting

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pat.domain.model.member.MyProfileContent
import com.pat.domain.usecase.auth.LogoutUseCase
import com.pat.domain.usecase.auth.SetUserKeyUseCase
import com.pat.domain.usecase.member.DeleteMemberUseCase
import com.pat.domain.usecase.member.GetMyProfileUseCase
import com.pat.domain.usecase.member.UpdateProfileImageUseCase
import com.pat.domain.usecase.member.UpdateProfileNicknameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SettingEvent {
    object GetMyProfileSuccess : SettingEvent()
    object GetMyProfileFailed : SettingEvent()

    object DeleteUserSuccess : SettingEvent()
    object DeleteUserFailed : SettingEvent()
    object LogoutSuccess : SettingEvent()
    object UpdateNicknameSuccess : SettingEvent()

    object DuplicatedNicknameException : SettingEvent()
    object UserNotFoundException : SettingEvent()
    object UnknownException : SettingEvent()

}
data class SettingUiState(
    val profileContent: MyProfileContent? = null
)

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase,
    private val updateProfileImageUseCase :UpdateProfileImageUseCase,
    private val updateProfileNicknameUseCase: UpdateProfileNicknameUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val setUserKeyUseCase: SetUserKeyUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<SettingEvent>()
    val event = _event.asSharedFlow()

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    init{
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

    fun updateProfileImage(uri: Uri?){
        viewModelScope.launch {
            val result = updateProfileImageUseCase(uri.toString())
            if (result.isSuccess) {
                getMyProfile()
            } else {
                handleException(result.exceptionOrNull()!!)
            }
        }
    }

    fun updateProfileNickname(nickname : String){
        viewModelScope.launch {
            val result = updateProfileNicknameUseCase(nickname)
            if (result.isSuccess) {
                getMyProfile()
                _event.emit(SettingEvent.UpdateNicknameSuccess)
            } else {
                handleException(result.exceptionOrNull()!!)
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            val result = logoutUseCase()
            if (result.isSuccess) {
                setUserKeyUseCase("")
                _event.emit(SettingEvent.LogoutSuccess)
                Log.e("custom", "로그아웃성공")
            } else {
                _event.emit(SettingEvent.DeleteUserFailed)
                Log.e("custom", "로그아웃실패")
            }
        }
    }

    private suspend fun handleException(error: Throwable){
        when (error){
            is InvaildRequestException -> _event.emit(SettingEvent.DuplicatedNicknameException)
            is UserNotFoundException -> _event.emit(SettingEvent.UserNotFoundException)
            else -> _event.emit(SettingEvent.UnknownException)
        }
    }

}