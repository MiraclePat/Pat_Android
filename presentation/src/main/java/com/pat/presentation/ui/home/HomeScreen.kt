package com.pat.presentation.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orhanobut.logger.Logger
import com.pat.presentation.R
import com.pat.presentation.ui.home.components.BarIcon
import com.pat.presentation.ui.home.components.HomeCategory
import com.pat.presentation.ui.home.components.HomeMyPat
import com.pat.presentation.ui.home.components.HomeTopBar
import com.pat.presentation.ui.home.components.Pats
import com.pat.presentation.ui.home.components.SearchTextField

@Composable
fun HomeScreenView(homeViewModel: HomeViewModel = hiltViewModel()) {
    val uiState by homeViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState.content) {
        Logger.t("MainTest").i("${uiState.content}")
    }
    Scaffold(
        topBar = {
            HomeTopBar(
                searchTextField = {
                    SearchTextField(
                        value = "",
//                        value = content,
                        hint = "어떤 팟을 찾고계신가요?",
                        onValueChange = {},
                    )
                },
                addButton = { BarIcon(onclick = {}, source = R.drawable.ic_add) },
                alarmButton = { BarIcon(onclick = {}, source = R.drawable.ic_alram) })
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
            Pats(content = uiState.content)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview4() {
    HomeScreenView()
}