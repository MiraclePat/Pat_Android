package com.pat.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.HomeBannerContent
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.usecase.pat.GetHomeBannerUseCase
import com.pat.domain.usecase.pat.GetHomePatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeUiState(
    val content: List<HomePatContent>? = null
)

data class BannerUiState(
    val content: HomeBannerContent? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomePatsUseCase: GetHomePatsUseCase,
    private val getHomeBannerUseCase: GetHomeBannerUseCase,
) : ViewModel() {
    private val _hotUiState = MutableStateFlow(HomeUiState())
    val hotUiState: StateFlow<HomeUiState> = _hotUiState.asStateFlow()
    private val _recentUiState = MutableStateFlow(HomeUiState())
    val recentUiState: StateFlow<HomeUiState> = _recentUiState.asStateFlow()
    private val _searchUiState = MutableStateFlow(HomeUiState())
    val searchUiState: StateFlow<HomeUiState> = _searchUiState.asStateFlow()
    private val _homeBanner = MutableStateFlow(BannerUiState())
    val homeBanner: StateFlow<BannerUiState> = _homeBanner.asStateFlow()
    private val size = 10


    init {
        viewModelScope.launch {
            val homeBannerResult = getHomeBannerUseCase()
            if (homeBannerResult.isSuccess) {
                val content = homeBannerResult.getOrThrow()
                _homeBanner.emit(BannerUiState(content))
            } else {
                Logger.t("MainTest").i("홈 pat 에러")
            }
        }
    }

    fun getHotPats(category: String?): Flow<PagingData<HomePatContent>> {
        return Pager(
            config = PagingConfig(pageSize = size),
            pagingSourceFactory = {
                HomePaging(
                    getHomePatsUseCase,
                    HomePatRequestInfo(sort = "HOT", size = size, category = category)
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun getRecentPats(category: String): Flow<PagingData<HomePatContent>> {
        return Pager(
            config = PagingConfig(pageSize = size),
            pagingSourceFactory = {
                HomePaging(
                    getHomePatsUseCase,
                    HomePatRequestInfo(sort = "LATEST", size = size, category = category)
                )
            }
        ).flow.cachedIn(viewModelScope)
    }


    //    fun requestByCategory(category: String) {
//        viewModelScope.launch {
//            val hotResult =
//                getHomePatsUseCase(HomePatRequestInfo(category = category, sort = "HOT"))
//            if (hotResult.isSuccess) {
//                val content = hotResult.getOrThrow()
//                _hotUiState.emit(HomeUiState(content = content))
//            } else {
//                Logger.t("MainTest").i("홈 팟 카테고리 에러")
//            }
//            val recentResult =
//                getHomePatsUseCase(HomePatRequestInfo(category = category, sort = "LATEST"))
//            if (recentResult.isSuccess) {
//                val content = recentResult.getOrThrow()
//                _recentUiState.emit(HomeUiState(content = content))
//            } else {
//                Logger.t("MainTest").i("홈 팟 카테고리 에러")
//            }
//        }
//    }

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