package com.pat.presentation.ui.home.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.pat.presentation.ui.common.LoadingProgressBar
import com.pat.presentation.ui.home.HomeViewModel
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.Typography


@Composable
fun HomeSearchView(
    modifier: Modifier = Modifier,
    searchValue: MutableState<String>,
    onSearchScreen: MutableState<Boolean>,
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val backPressedState by remember { mutableStateOf(true) }
    val content = homeViewModel.searchPats.collectAsLazyPagingItems()
    var searchTextView by remember { mutableStateOf(searchValue) }

    BackHandler(enabled = backPressedState) {
        onSearchScreen.value = false
        searchValue.value = ""
    }

    Scaffold(
        topBar = {
            SearchTopBar(
                searchValue = searchValue,
                inputEnter = {
                    searchTextView = searchValue
                },
                onSearchScreen = onSearchScreen,
                homeViewModel = homeViewModel
            )
        },
    ) { innerPadding ->
        if (content.loadState.append is LoadState.Loading || content.loadState.refresh is LoadState.Loading || content.loadState.prepend is LoadState.Loading) {
            LoadingProgressBar()
        } else {
            if (content.itemCount == 0) {
                Column(
                    modifier = modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Column {
                            Text("검색 결과가 없습니다.", style = Typography.labelMedium, color = Gray800)
                            Spacer(modifier = modifier.padding(bottom = 13.dp))
                            Text("다른 검색어를 입력하시거나", style = Typography.labelSmall, color = Gray600)
                            Spacer(modifier = modifier.padding(bottom = 4.dp))
                            Text(
                                "철자와 띄어쓰기를 확인해보세요.",
                                style = Typography.labelSmall,
                                color = Gray600
                            )
                        }
                    }
                    Spacer(modifier = modifier.padding(bottom = 24.dp)) // 임의 값
                    Column(modifier.fillMaxWidth()) {
                        // 추후 랜덤 기능 생성 되면 구현
//                    Pats(navController, content = content, text = "유저닉네임 님! 이 팟은 어떠세요?")
                    }
                    Spacer(modifier = modifier.padding(bottom = 47.dp))
                }
            } else {
                Column(
                    modifier = modifier
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = modifier.padding(start = 20.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "'${searchTextView.value.ifEmpty { "전체" }}' 결과입니다.",
                            style = Typography.titleLarge
                        )
                        Spacer(modifier = modifier.weight(1f)) // 임의 값
                        Text(
                            "총 ${content.itemCount}개 검색결과.",
                            style = Typography.labelSmall,
                            color = Gray800
                        )
                    }
                    LazyVerticalGrid(
                        modifier = modifier.padding(
                            top = 20.dp,
                            start = 30.dp,
                            end = 30.dp,
                            bottom = 24.dp
                        ),
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(content.itemCount) { idx ->
                            content[idx]?.let { pat ->
                                HomePats(
                                    title = pat.patName,
                                    category = pat.category,
                                    nowPerson = pat.nowPerson,
                                    maxPerson = pat.maxPerson,
                                    startDate = pat.startDate,
                                    imgUri = pat.repImg,
                                    location = pat.location.ifEmpty { "어디서나 가능" },
                                    onClick = {
                                        navController.navigate("patDetail/${pat.patId}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
