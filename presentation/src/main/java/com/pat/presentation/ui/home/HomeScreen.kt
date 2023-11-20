package com.pat.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.orhanobut.logger.Logger
import com.pat.presentation.R
import com.pat.presentation.ui.common.BarIcon
import com.pat.presentation.ui.home.components.HomeCategory
import com.pat.presentation.ui.home.components.HomeMyPat
import com.pat.presentation.ui.home.components.HomeTopBar
import com.pat.presentation.ui.home.components.SearchTextField

@Composable
fun HomeScreenView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToPost: () -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val searchValue = remember { mutableStateOf("") }

    LaunchedEffect(uiState.content) {
        Logger.t("MainTest").i("${uiState.content}")
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                searchTextField = {
                    SearchTextField(state = searchValue)
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeMyPat()
            HomeCategory()
//            HatPat()
            Spacer(Modifier.size(20.dp))
//            RecentPat()
        }
    }
}