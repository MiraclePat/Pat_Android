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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pat.presentation.R
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectImage(modifier: Modifier = Modifier, imageIdx: Int = -1) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
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
                showBottomSheet = true
            },
        contentAlignment = Alignment.Center
    ) {

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                FinalButton(text = "사진선택", backColor = Color.White, textColor = PrimaryMain)
                FinalButton(text = "갤러리에서 가져오기", backColor = Color.White, textColor = PrimaryMain)
            }
        }

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

                if (imageIdx == -1){
                    Text(text="사진 첨부하기",
                        style = Typography.labelSmall,
                      )
                }
                else Text("사진$imageIdx 첨부하기", style = Typography.labelSmall)
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