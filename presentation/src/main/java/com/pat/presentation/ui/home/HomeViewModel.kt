package com.pat.presentation.ui.home

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
import kotlinx.coroutines.launch
import javax.inject.Inject


data class BannerUiState(
    val content: HomeBannerContent? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomePatsUseCase: GetHomePatsUseCase,
    private val getHomeBannerUseCase: GetHomeBannerUseCase,
) : ViewModel() {
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

    fun getPats(
        sort: String? = null,
        category: String? = null,
        query: String? = null
    ): Flow<PagingData<HomePatContent>> {
        return Pager(
            config = PagingConfig(pageSize = size),
            pagingSourceFactory = {
                HomePaging(
                    getHomePatsUseCase,
                    HomePatRequestInfo(
                        sort = sort,
                        size = size,
                        category = category,
                        query = query
                    )
                )
            }
        ).flow.cachedIn(viewModelScope)
    }
}