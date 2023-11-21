package com.pat.presentation.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pat.domain.model.pat.CreatePatDetail
import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.usecase.pat.CreatePatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class PostUiState(
    val content: List<HomePatContent>? = null
)

@HiltViewModel
class PostViewModel @Inject constructor(
    private val createPatUseCase: CreatePatUseCase,
) : ViewModel() {
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