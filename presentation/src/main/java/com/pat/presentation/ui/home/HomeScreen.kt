package com.pat.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.orhanobut.logger.Logger
import com.pat.presentation.R
import com.pat.presentation.ui.common.BarIcon
import com.pat.presentation.ui.common.SnackBar
import com.pat.presentation.ui.home.components.HomeCategory
import com.pat.presentation.ui.home.components.HomeMyPat
import com.pat.presentation.ui.home.components.HomeSearchView
import com.pat.presentation.ui.home.components.HomeTopBar
import com.pat.presentation.ui.home.components.Pats
import com.pat.presentation.ui.home.components.SearchTextField
import com.pat.presentation.util.POST

@Composable
fun HomeScreenView(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val scrollState = rememberScrollState()
    val searchValue = remember { mutableStateOf("") }
    val categoryState = rememberSaveable { mutableStateOf("전체") }
    val onSearchScreen = remember { mutableStateOf(false) }

    val errorMessage = remember { mutableStateOf("") }
    var bannerContent by remember { mutableStateOf(HomeEvent.BannerSuccess().content) }

    LaunchedEffect(Unit) {
        homeViewModel.event.collect {
            when (it) {
                is HomeEvent.BannerSuccess -> {
                    bannerContent = it.content
                }
                is HomeEvent.BannerFailed -> {
                    errorMessage.value = "배너를 불러오는데 실패"
                }
            }
        }
    }


    if (!onSearchScreen.value) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    searchTextField = {
                        SearchTextField(
                            state = searchValue,
                            homeViewModel = homeViewModel,
                            inputEnter = {
                                onSearchScreen.value = true
                            })
                    },
                    addButton = {
                        BarIcon(onclick = {
                            navController.navigate(POST)
                        }, source = R.drawable.ic_add)
                    },
                    alarmButton = { BarIcon(onclick = {}, source = R.drawable.ic_bell) })
            },
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(scrollState),
            ) {
                val hotPats = homeViewModel.hotPats.collectAsLazyPagingItems()
                val recentPats = homeViewModel.recentPats.collectAsLazyPagingItems()

                HomeMyPat(content = bannerContent, navController = navController)
                HomeCategory(state = categoryState, homeViewModel = homeViewModel)
                Pats(
                    modifier = modifier,
                    navController = navController,
                    text = stringResource(id = R.string.home_hot_pat_title),
                    uiState = hotPats
                )
                Spacer(Modifier.size(20.dp))
                Pats(
                    modifier = modifier,
                    navController = navController,
                    text = stringResource(id = R.string.home_recent_pat_title),
                    uiState = recentPats
                )
            }

            if (errorMessage.value.isNotEmpty()) {
                SnackBar(errorMessage)
            }
        }
    } else {
        HomeSearchView(
            searchValue = searchValue,
            onSearchScreen = onSearchScreen,
            navController = navController,
            homeViewModel = homeViewModel
        )
    }
}