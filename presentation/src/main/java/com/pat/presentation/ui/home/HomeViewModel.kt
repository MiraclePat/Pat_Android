package com.pat.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.usecase.pat.GetHomePatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeUiState(
    val content: List<HomePatContent>? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomePatsUseCase: GetHomePatsUseCase,
) : ViewModel() {
    private val _hotUiState = MutableStateFlow(HomeUiState())
    val hotUiState: StateFlow<HomeUiState> = _hotUiState.asStateFlow()
    private val _recentUiState = MutableStateFlow(HomeUiState())
    val recentUiState: StateFlow<HomeUiState> = _recentUiState.asStateFlow()
    private val _searchUiState = MutableStateFlow(HomeUiState())
    val searchUiState: StateFlow<HomeUiState> = _searchUiState.asStateFlow()
    private val _homePat = MutableStateFlow(HomeUiState())
    val homePat: StateFlow<HomeUiState> = _homePat.asStateFlow()

    init {
        viewModelScope.launch {
            val hotResult = getHomePatsUseCase(HomePatRequestInfo(sort = "HOT"))
            if (hotResult.isSuccess) {
                val content = hotResult.getOrThrow()
                _hotUiState.emit(HomeUiState(content = content))
            } else {
                Logger.t("MainTest").i("홈 pat 에러")
            }

            val recentResult =
                getHomePatsUseCase(HomePatRequestInfo(sort = "LATEST"))
            if (recentResult.isSuccess) {
                val content = recentResult.getOrThrow()
                _recentUiState.emit(HomeUiState(content = content))
            } else {
                Logger.t("MainTest").i("홈 pat 에러")
            }

            val homePatResult =
                getHomePatsUseCase(HomePatRequestInfo(state = "IN_PROGRESS"))
            if (homePatResult.isSuccess) {
                val content = homePatResult.getOrThrow()
                _homePat.emit(HomeUiState(content = content))
            } else {
                Logger.t("MainTest").i("홈 pat 에러")
            }
        }
    }

    fun requestByCategory(category: String) {
        viewModelScope.launch {
            val hotResult =
                getHomePatsUseCase(HomePatRequestInfo(category = category, sort = "HOT"))
            if (hotResult.isSuccess) {
                val content = hotResult.getOrThrow()
                _hotUiState.emit(HomeUiState(content = content))
            } else {
                Logger.t("MainTest").i("홈 팟 카테고리 에러")
            }
            val recentResult =
                getHomePatsUseCase(HomePatRequestInfo(category = category, sort = "LATEST"))
            if (recentResult.isSuccess) {
                val content = recentResult.getOrThrow()
                _recentUiState.emit(HomeUiState(content = content))
            } else {
                Logger.t("MainTest").i("홈 팟 카테고리 에러")
            }
        }
    }

    fun searchPat(query: String) {
        viewModelScope.launch {
            val result = getHomePatsUseCase(HomePatRequestInfo(query = query))
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _searchUiState.emit(HomeUiState(content = content))
            } else {
                Logger.t("MainTest").i("홈 search 에러")
            }
        }
    }
}