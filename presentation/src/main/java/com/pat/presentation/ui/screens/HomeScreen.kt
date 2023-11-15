package com.pat.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pat.presentation.R
import com.pat.presentation.ui.components.HatPat
import com.pat.presentation.ui.components.HomeCategory
import com.pat.presentation.ui.components.HomeMyPat
import com.pat.presentation.ui.components.RecentPat
import com.pat.presentation.ui.navigations.BarIcon
import com.pat.presentation.ui.navigations.HomeTopBar
import com.pat.presentation.ui.navigations.SearchTextField


@Composable
fun HomeScreenView() {
//    val content by viewModel.content.collectAsState()
    val scrollState = rememberScrollState()

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
            HatPat()
            Spacer(Modifier.size(20.dp))
            RecentPat()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview4() {
    HomeScreenView()
}