package com.pat.presentation.ui.post.components

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pat.presentation.R
import com.pat.presentation.ui.common.SelectButton
import com.pat.presentation.ui.post.PostViewModel
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostRepImageView(
    modifier: Modifier = Modifier,
    navController: NavController,
    bitmap: Bitmap?,
    viewModel: PostViewModel,
) {

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            viewModel.getBitmapByUri(uri, "REP")
        }
    )
    Box(
        modifier
            .background(Gray100)
            .fillMaxWidth()
            .height(160.dp)
            .clickable {
            },
        contentAlignment = Alignment.Center
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
        Row(
            modifier = modifier
                .width(141.dp)
                .height(36.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(1.dp, color = PrimaryMain, RoundedCornerShape(4.dp))
                .background(White)
                .clickable {
                    showBottomSheet = true
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "썸네일 추가하기",
                style = Typography.labelMedium,
                color = PrimaryMain,
            )
            Spacer(modifier = modifier.size(4.dp))
            Icon(
                modifier = modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "썸네일 추가하기"
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        "아래 방법 중 하나를 선택해주세요.",
                        style = Typography.titleLarge,
                        color = Gray800
                    )
                    Spacer(modifier = modifier.padding(16.dp))

                    SelectButton(
                        text = "사진촬영",
                        onClick = {
                            navController.navigate("camera/REP")
                        },
                        backColor = Color.White,
                        textColor = PrimaryMain,
                        cornerSize = 100.dp,
                        stokeColor = PrimaryMain,
                        stokeWidth = 1.dp
                    )

                    Spacer(modifier = modifier.padding(10.dp))

                    SelectButton(
                        stokeColor = PrimaryMain, stokeWidth = 1.dp,
                        text = "갤러리에서 가져오기",
                        onClick = {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                            showBottomSheet = false
                        },
                        cornerSize = 100.dp,
                        backColor = Color.White,
                        textColor = PrimaryMain
                    )
                }
                Spacer(modifier = modifier.padding(16.dp))

            }
        }
    }


}