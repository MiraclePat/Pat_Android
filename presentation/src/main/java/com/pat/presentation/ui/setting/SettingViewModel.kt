package com.pat.presentation.ui.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pat.domain.model.member.MyProfileContent
import com.pat.domain.usecase.member.DeleteMemberUseCase
import com.pat.domain.usecase.member.GetMyProfileUseCase
import com.pat.presentation.util.resultException
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