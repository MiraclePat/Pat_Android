package com.pat.presentation.ui.pat

import android.content.Context
import android.graphics.Bitmap
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pat.presentation.R
import com.pat.presentation.ui.CameraPreview
import com.pat.presentation.ui.common.SelectButton
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProofImageView(
    navController: NavController,
    modifier: Modifier = Modifier,
    imageIdx: Int = -1,
    hasSource: String = "",
    realTime: Boolean = true,
    bitmap: Bitmap?,
    viewModel: PattingViewModel,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var isGallery by remember { mutableStateOf(false) }


    val roundedCornerShape = if (imageIdx == -1) RoundedCornerShape(
        topStart = 4.dp,
        topEnd = 4.dp
    ) else RoundedCornerShape(4.dp)
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            showBottomSheet = false
            viewModel.getBitmapByUri(uri)
            selectedImageUri = uri
        }
    )

    Box(
        modifier
            .height(140.dp)
            .width(130.dp)
            .clip(roundedCornerShape)
            .background(Gray300)
            .clickable {
                showBottomSheet = true
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
        if(isGallery){
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
                            "아래 인증방법 중 하나를 선택해주세요.",
                            style = Typography.titleLarge,
                            color = Gray800
                        )
                        Spacer(modifier = modifier.padding(16.dp))

                        SelectButton(
                            text = "사진촬영",
                            onClick = {
                                navController.navigate("pattingCamera")
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
                            isGallery=true
                        },
                        cornerSize = 100.dp,
                        backColor = Color.White,
                        textColor = PrimaryMain
                    )
                    Spacer(modifier = modifier.padding(10.dp))

                }
            }
        }



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
fun SettingPattingCamera(
    navController: NavController,
    viewModel: PattingViewModel,
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
                    takeProofPhoto(
                        navController = navController,
                        context = context,
                        controller = controller,
                        onPhotoTaken = viewModel::onTakePhoto,
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

private fun takeProofPhoto(
    navController: NavController,
    context: Context,
    controller: LifecycleCameraController,
    onPhotoTaken: (ImageProxy) -> Unit,
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                onPhotoTaken(image)
                navController.popBackStack()
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
            }
        }
    )
}