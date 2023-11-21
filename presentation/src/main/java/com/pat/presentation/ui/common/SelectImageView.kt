package com.pat.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pat.presentation.R
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Typography

@Composable
fun SelectImage(modifier: Modifier = Modifier, imageIdx: Int = -1, hasSource: String = "") {
    val roundedCornerShape = if (imageIdx == -1) RoundedCornerShape(
        topStart = 4.dp,
        topEnd = 4.dp
    ) else RoundedCornerShape(4.dp)

    Box(
        modifier
            .height(140.dp)
            .width(130.dp)
            .clip(roundedCornerShape)
            .background(Gray100)
            .clickable {
                // TODO click Event
            },
        contentAlignment = Alignment.Center
    ) {
        if (hasSource != "") {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    tint = Gray500
                )
                Box() {
                    if (imageIdx == -1) Text("사진 첨부하기", style = Typography.labelSmall)
                    else Text("사진$imageIdx 첨부하기", style = Typography.labelSmall)
                }
            }
        }
    }
}

@Composable
fun SelectImageList(modifier: Modifier = Modifier) {
    val imageList = listOf<Int>(1, 2, 3, 4, 5)

    LazyRow() {
        items(imageList) { imageIdx ->
            SelectImage(imageIdx = imageIdx)
            Spacer(modifier = modifier.padding(horizontal = 10.dp))
        }
    }
}