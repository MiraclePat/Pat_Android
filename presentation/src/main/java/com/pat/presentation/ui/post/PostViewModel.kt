package com.pat.presentation.ui.post

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.CreatePatDetail
import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.usecase.pat.CreatePatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class PostUiState(
    val content: List<HomePatContent>? = null
)

@HiltViewModel
class PostViewModel @Inject constructor(
    private val createPatUseCase: CreatePatUseCase,
) : ViewModel() {

    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap = _bitmap.asStateFlow()
    fun onTakePhoto(bitmap: Bitmap) {
        _bitmap.value = bitmap
        Logger.t("bitmaps").i("viewmodel에서${bitmap}")
    }

    fun post(
        pat: CreatePatDetail
    ) {
        viewModelScope.launch {
            val result = createPatUseCase(
                CreatePatInfo("", "", listOf(), listOf(), pat)
            )
            if (result.isSuccess) {
                //TODO 성공
            } else {
                //TODO 에러 처리
            }
        }
    }
}