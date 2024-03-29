package com.pat.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.orhanobut.logger.Logger
import com.pat.domain.model.exception.ForbiddenException
import com.pat.domain.model.pat.HomeBannerContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.usecase.auth.GetLoginStatusUseCase
import com.pat.domain.usecase.pat.GetHomeBannerUseCase
import com.pat.domain.usecase.pat.GetHomePatsUseCase
import com.pat.presentation.ui.pat.ParticipateEvent
import com.pat.presentation.util.resultException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeEvent {
    data class BannerSuccess(val content: HomeBannerContent? = null) : HomeEvent()
    object BannerFailed : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomePatsUseCase: GetHomePatsUseCase,
    private val getHomeBannerUseCase: GetHomeBannerUseCase,
    private val getLoginStatusUseCase: GetLoginStatusUseCase,
) : ViewModel() {
    private val size = 10

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()


    private val _category = MutableStateFlow("전체")
    val category: StateFlow<String> = _category.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val hotPats = _category.flatMapLatest { category ->
        Pager(
            config = PagingConfig(pageSize = size),
            pagingSourceFactory = {
                HomePaging(
                    getHomePatsUseCase,
                    setPats(category = category, sort = "HOT")
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val recentPats = _category.flatMapLatest { category ->
        Pager(
            config = PagingConfig(pageSize = size),
            pagingSourceFactory = {
                HomePaging(
                    getHomePatsUseCase,
                    setPats(category = category, sort = "LATEST")
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchPats = _query.flatMapLatest { query ->
        Pager(
            config = PagingConfig(pageSize = size),
            pagingSourceFactory = {
                HomePaging(
                    getHomePatsUseCase,
                    setPats(query = query)
                )
            }
        ).flow.cachedIn(viewModelScope)
    }


    init {
        viewModelScope.launch {
//            val loginResult = getLoginStatusUseCase()
//            if (loginResult.isSuccess) {
//                Logger.t("MainTest").i("에러. ${loginResult}")
//                if (loginResult.getOrThrow() == true){
//                    Logger.t("MainTest").i("진입")
//                    getBanner()}
                getBanner()
//            } else {
//                Logger.t("MainTest").i("비회원입니다")
//            }
        }
    }

    private fun getBanner() {
        viewModelScope.launch {
            val homeBannerResult = getHomeBannerUseCase()
            if (homeBannerResult.isSuccess) {
                val content = homeBannerResult.getOrThrow()
                _event.emit(HomeEvent.BannerSuccess(content))
            } else {
                _event.emit(HomeEvent.BannerFailed)
                val error = homeBannerResult.exceptionOrNull()
                resultException(error)
            }
        }
    }

    private fun setPats(
        sort: String? = null,
        category: String? = null,
        query: String? = null
    ): HomePatRequestInfo {
        return HomePatRequestInfo(
            sort = sort,
            category = category,
            query = query
        )
    }

    fun setCategory(inputCategory: String) {
        viewModelScope.launch {
            _category.emit(inputCategory)
        }
    }

    fun setQuery(inputQuery: String) {
        viewModelScope.launch {
            _query.emit(inputQuery)
        }
    }
}