package com.pat.presentation.ui.pat.components

import androidx.core.content.ContextCompat
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.orhanobut.logger.Logger
import com.pat.presentation.R
import com.pat.presentation.model.PatBitmap
import com.pat.presentation.ui.CameraPreview
import com.pat.presentation.ui.common.SelectButton
import com.pat.presentation.ui.pat.PatUpdateViewModel
import com.pat.presentation.ui.post.PostViewModel
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateSelectImage(
    navController: NavController,
    modifier: Modifier = Modifier,
    imageIdx: Int = -1,
    realTime: Boolean = true,
    bitmap: Bitmap?,
    bitmapType: String,
    viewModel: PatUpdateViewModel,
    selectable: Boolean = true,
    hasSource: Boolean = false,
    originalIdx: Int = -1
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var isGallery by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var updateState by remember { mutableStateOf("false") }
    if(bitmap != null || selectedImageUri != null){
        updateState = "true"
    }

    val roundedCornerShape = if (imageIdx == -1) RoundedCornerShape(
        topStart = 4.dp,
        topEnd = 4.dp
    ) else RoundedCornerShape(4.dp)

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                showBottomSheet = false
                viewModel.getBitmapByUri(uri.toString(), bitmapType, updateState, originalIdx)
                selectedImageUri = uri
            }
        },

    )
    Box(
        modifier
            .height(140.dp)
            .width(130.dp)
            .clip(roundedCornerShape)
            .background(Gray100)
            .clickable {
                if (selectable) showBottomSheet = true
            },
        contentAlignment = Alignment.Center
    ) {
        if (!hasSource) {
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

        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
        if (isGallery) {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                if (realTime) {
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
                                navController.navigate("updateCamera/${bitmapType}/${updateState}/${originalIdx}")
                            },
                            backColor = Color.White,
                            textColor = PrimaryMain,
                            cornerSize = 100.dp,
                            stokeColor = PrimaryMain,
                            stokeWidth = 1.dp
                        )
                    }

                } else {
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("이 팟은 갤러리 인증만 가능해요.", style = Typography.titleLarge, color = Gray800)
                        Text(
                            "아래 버튼을 눌러 사진을 불러와주세요.",
                            style = Typography.titleMedium,
                            color = Gray600
                        )
                        Spacer(modifier = modifier.padding(13.dp))
                    }
                }
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = modifier.padding(10.dp))

                    SelectButton(
                        stokeColor = PrimaryMain, stokeWidth = 1.dp,
                        text = "갤러리에서 가져오기",
                        onClick = {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                            isGallery = true
                        },
                        cornerSize = 100.dp,
                        backColor = Color.White,
                        textColor = PrimaryMain
                    )
                    Spacer(modifier = modifier.padding(10.dp))

                }
            }
        }


    }
}

@Composable
fun UpdateSettingCamera(
    navController: NavController,
    viewModel: PatUpdateViewModel,
    bitmapType: String,
    updateState: String?,
    originalIdx: String?,
) {
    val context: Context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CameraPreview(
            controller = controller,
            modifier = Modifier
                .fillMaxSize()
        )

        IconButton(
            onClick = {
                controller.cameraSelector =
                    if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                        CameraSelector.DEFAULT_FRONT_CAMERA
                    } else CameraSelector.DEFAULT_BACK_CAMERA
            },
            modifier = Modifier
                .offset(16.dp, 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Cameraswitch,
                contentDescription = "Switch camera"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            IconButton(
                onClick = {
                    updateTakePhoto(
                        navController = navController,
                        context = context,
                        controller = controller,
                        onPhotoTaken = viewModel::onTakePhoto,
                        bitmapType = bitmapType,
                        updateState = updateState,
                        originalIdx = originalIdx
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Take photo"
                )
            }
        }
    }
}

private fun updateTakePhoto(
    navController: NavController,
    context: Context,
    controller: LifecycleCameraController,
    onPhotoTaken: (ImageProxy, String, String?, String?) -> Unit,
    bitmapType: String,
    updateState: String?,
    originalIdx: String?,
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                onPhotoTaken(image, bitmapType, updateState, originalIdx)
                navController.popBackStack()
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
            }
        }
    )
}

@Composable
fun UpdateSelectImageList(
    navController: NavController,
    modifier: Modifier = Modifier,
    bitmapList: List<Bitmap?>,
    bitmapType: String,
    viewModel: PatUpdateViewModel,
) {

    val maxSize = IntArray(5 - bitmapList.size) { it + 1 + bitmapList.size }

    LazyRow() {
        itemsIndexed(bitmapList) { idx, bitmap ->
            UpdateSelectImage(
                navController = navController,
                imageIdx = 0,
                bitmap = bitmap,
                bitmapType = bitmapType,
                viewModel = viewModel,
                hasSource = true,
                originalIdx = idx,
            )
            Spacer(modifier = modifier.padding(horizontal = 10.dp))
        }
        items(maxSize.toList()) { idx ->
            UpdateSelectImage(
                navController = navController,
                imageIdx = idx,
                bitmap = null,
                bitmapType = bitmapType,
                viewModel = viewModel,
                hasSource = false,
                originalIdx = idx-1
            )
            Spacer(modifier = modifier.padding(horizontal = 10.dp))
        }
    }
}