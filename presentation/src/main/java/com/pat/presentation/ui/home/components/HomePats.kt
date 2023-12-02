package com.pat.presentation.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBox
import com.pat.presentation.ui.common.SimpleTextView
import com.pat.presentation.ui.home.HomeUiState
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.Typography
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Pats(
    navController: NavController,
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    text: String,
) {
    val content = uiState.content

    Column(modifier.padding(vertical = 20.dp, horizontal = 16.dp)) {
        Text(
            text = text,
            style = Typography.titleLarge
        )
        Spacer(Modifier.size(12.dp))
        val scrollState = rememberScrollState()
        Row(modifier.horizontalScroll(scrollState)) {
            content?.forEach { pat ->
                HomePats(
                    title = pat.patName,
                    category = pat.category.ifEmpty { "어디서나 가능" },
                    nowPerson = pat.nowPerson,
                    maxPerson = pat.maxPerson,
                    startDate = pat.startDate,
                    imgUri = pat.repImg,
                    location = pat.location,
                    onClick = {
                        navController.navigate("patDetail/${pat.patId}") }
                )
                Spacer(Modifier.size(10.dp))
            }
        }
    }
}

@Composable
fun HomePats(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    location: String,
    startDate: String,
    imgUri: String,
    nowPerson: Int,
    maxPerson: Int,
    category: String,
) {
    Column(
        modifier
            .wrapContentSize()
            .clickable { onClick() }) {
        Box() {
            GlideImage(
                modifier = modifier
                    .size(140.dp, 140.dp)
                    .clip(RoundedCornerShape(12.dp)),
                imageModel = { imgUri })
            CategoryBox(
                modifier = modifier.padding(8.dp),
                category = category,
                isSelected = true
            )
        }
        Spacer(modifier.size(10.dp))
        Text(text = title, style = Typography.labelMedium)
        Spacer(modifier.size(6.dp))
        SimpleTextView(text = location, vectorResource = R.drawable.ic_map, iconColor = Gray700)
        SimpleTextView(
            text = startDate,
            vectorResource = R.drawable.ic_calendar,
            iconColor = Gray700
        )
        SimpleTextView(
            text = "$nowPerson / $maxPerson",
            vectorResource = R.drawable.ic_user,
            iconColor = Gray700
        )
    }
}
