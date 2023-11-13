package com.pat.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
                addButton = { BarIcon(onclick = {}, imageVector = Icons.Rounded.Add) },
                alarmButton = { BarIcon(onclick = {}, imageVector = Icons.Rounded.Notifications) })
        },
        bottomBar = {
            BottomAppBar() {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            HomeMyPat()
            HomeCategory()
            HatPat()
            RecentPat()
        }
    }
}

