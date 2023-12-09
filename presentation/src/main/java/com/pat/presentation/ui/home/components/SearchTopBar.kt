package com.pat.presentation.ui.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pat.presentation.R
import com.pat.presentation.ui.common.BarIcon
import com.pat.presentation.ui.home.HomeViewModel
import com.pat.presentation.ui.theme.Gray800

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    searchValue: MutableState<String>,
    inputEnter: () -> Unit,
    onSearchScreen: MutableState<Boolean>,
    homeViewModel: HomeViewModel
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 22.dp)
            .height(38.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BarIcon(modifier = modifier.size(24.dp), source = R.drawable.ic_back_arrow, tint = Gray800,
            onclick = {
                onSearchScreen.value = false
                searchValue.value = ""
            })
        Spacer(modifier = modifier.padding(start = 24.dp))
        Spacer(modifier = modifier.weight(1.0f))
        SearchTextField(state = searchValue, inputEnter = inputEnter, text = searchValue.value, homeViewModel = homeViewModel)
    }
}
