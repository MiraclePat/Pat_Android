package com.pat.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pat.presentation.R
import com.pat.presentation.ui.common.BarIcon
import com.pat.presentation.ui.home.components.HomeCategory
import com.pat.presentation.ui.home.components.HomeMyPat
import com.pat.presentation.ui.home.components.HomeSearchView
import com.pat.presentation.ui.home.components.HomeTopBar
import com.pat.presentation.ui.home.components.Pats
import com.pat.presentation.ui.home.components.SearchTextField

@Composable
fun HomeScreenView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToPost: () -> Unit,
    navController: NavController,
) {
    val hotUiState by homeViewModel.hotUiState.collectAsState()
    val recentUiState by homeViewModel.recentUiState.collectAsState()
    val homePat by homeViewModel.homePat.collectAsState()
    val scrollState = rememberScrollState()
    val searchValue = remember { mutableStateOf("") }
    val categoryState = remember { mutableStateOf("전체") }
    val onSearchScreen = remember { mutableStateOf(false) }
    val searchResult = remember { mutableStateOf(true) } // 임시


    if (!onSearchScreen.value) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    searchTextField = {
                        SearchTextField(
                            state = searchValue,
                            inputEnter = { onSearchScreen.value = true })
                    },
                    addButton = {
                        BarIcon(onclick = {
                            onNavigateToPost()
                        }, source = R.drawable.ic_add)
                    },
                    alarmButton = { BarIcon(onclick = {}, source = R.drawable.ic_bell) })
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(scrollState),
            ) {
                HomeMyPat(content = homePat.content, navController = navController)
                HomeCategory(state = categoryState)
                Pats(
                    navController,
                    uiState = hotUiState,
                    text = stringResource(id = R.string.home_hot_pat_title),
                )
                Spacer(Modifier.size(20.dp))
                Pats(
                    navController,
                    uiState = recentUiState,
                    text = stringResource(id = R.string.home_recent_pat_title),
                )
            }
        }
    } else {
        HomeSearchView(
            searchValue = searchValue,
            onSearchScreen = onSearchScreen,
            navController = navController,
            searchResult = searchResult
        )
    }
}
