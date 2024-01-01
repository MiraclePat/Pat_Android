package com.pat.presentation.ui.setting

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
import com.pat.domain.model.member.MyProfileContent
import com.pat.presentation.R
import com.pat.presentation.ui.common.Divider
import com.pat.presentation.ui.common.SnackBar
import com.pat.presentation.ui.common.setUnderLine
import com.pat.presentation.ui.navigations.BottomNavItem
import com.pat.presentation.ui.setting.components.SettingBox
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.Primary100
import com.pat.presentation.ui.theme.Primary50
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.SystemBlue
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import com.pat.presentation.util.ACCOUNT
import com.pat.presentation.util.ALARM
import com.pat.presentation.util.ANNOUNCE
import com.pat.presentation.util.CERTIFICATION
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun SettingScreenBody(
    modifier: Modifier = Modifier,
    viewState: MutableState<String>,
    content: MyProfileContent?,
    navController: NavController,
    viewModel: SettingViewModel
) {
    val tempLoginState = true
    var updateNameState by remember { mutableStateOf<Boolean>(false) }
    var updateName: MutableState<String> = rememberSaveable {
        mutableStateOf("")
    }
    val errorMessage = remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            viewModel.updateProfileImage(uri)
        }
    )

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                is SettingEvent.UpdateNicknameSuccess -> {
                    errorMessage.value = "정상적으로 변경되었습니다."
                    updateNameState = false
                }

                is SettingEvent.DuplicatedNicknameException -> {
                    errorMessage.value = "중복된 닉네임입니다."
                }

                is SettingEvent.UserNotFoundException -> {
                    errorMessage.value = "로그인을 다시 시도해주세요"
                }

                else -> {}

            }
        }
    }
    @Composable
    fun patCount(modifier: Modifier = Modifier, text: String, count: Int) {
        Column(
            modifier.clickable {
                navController.navigate(CERTIFICATION + "?state=${text}") {
                    navController.graph.startDestinationRoute?.let {
                        popUpTo(it) { saveState = true }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text, style = Typography.bodySmall, color = Gray600)
            Spacer(modifier = modifier.size(13.dp))
            Text("${count}개", style = Typography.titleLarge, color = PrimaryMain, fontSize = 18.sp)
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 41.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier
                .size(80.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Box(
                modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(1.dp, Gray100, CircleShape)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },

                ) {
                GlideImage(
                    modifier = modifier
                        .size(140.dp, 140.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    imageModel = { content?.profileImg })
            }
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_white_add),
                contentDescription = null,
                modifier = modifier
                    .padding(4.dp)
                    .size(18.dp),
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = modifier.size(12.dp))
        if (tempLoginState) {
            if (updateNameState) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    NicknameTextField(
                        state = updateName
                    )
                    NicknameUpdateButton(
                        onClick = { viewModel.updateProfileNickname(updateName.value) },
                        shape = RoundedCornerShape(100.dp),
                        fontSize = 12.sp,
                        text = "변경하기",
                    )
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.clickable {
                    }) {
                    Text(
                        content?.nickname.toString(),
                        style = Typography.titleLarge,
                        color = Gray800
                    )
                    Icon(
                        modifier = modifier
                            .padding(start = 2.dp)
                            .clickable { updateNameState = true },
                        imageVector = ImageVector.vectorResource(R.drawable.ic_pencil),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                }
            }

        } else {
            Text(
                modifier = setUnderLine,
                text = "로그인/ 회원가입 하러 가기",
                style = Typography.titleLarge,
                color = Gray800
            )
        }
        Row(
            modifier
                .padding(top = 34.dp)
                .height(51.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            patCount(modifier = modifier.weight(1f), text = "완료한 팟", count = content?.finPats ?: 0)
            Box(
                modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(Primary100)
            )
            patCount(modifier = modifier.weight(1f), text = "개설한 팟", count = content?.openPats ?: 0)
        }
        Divider(modifier = modifier.padding(top = 24.dp))
        SettingBox(text = ACCOUNT, onClick = { viewState.value = ACCOUNT })
        SettingBox(text = ALARM, onClick = { viewState.value = ALARM })
        SettingBox(text = ANNOUNCE, onClick = { viewState.value = ANNOUNCE })
        SettingBox(
            text = "버전 정보",
            content = { Text("1.0", style = Typography.bodySmall, color = Gray500) })

        if (errorMessage.value.isNotEmpty()) {
            SnackBar(errorMessage)
        }
    }
}

@Composable
fun NicknameUpdateButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isSelected: Boolean = false,
    shape: Shape,
    fontSize: TextUnit,
) {

    Button(
        modifier = modifier
            .width(58.dp)
            .height(26.dp),
        shape = shape,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary50
        ),
        enabled = enabled,
        border = BorderStroke(1.dp, PrimaryMain),
        onClick = {
            onClick()
        },
    ) {
        Text(
            style = Typography.displaySmall,
            text = text,
            fontSize = fontSize,
            color = PrimaryMain
        )
    }
}

@Composable
fun NicknameTextField(
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    state: MutableState<String> = rememberSaveable {
        mutableStateOf("")
    },
    style: TextStyle = Typography.titleLarge,
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(modifier = modifier
        .padding(end = 2.dp)
        .clip(RoundedCornerShape(4.dp))
        .onFocusChanged {
            isFocused = it.hasFocus
        }
        .border(1.dp, SystemBlue, RoundedCornerShape(4.dp)),

        value = state.value,
        onValueChange = {
            state.value = it
        },
        cursorBrush = SolidColor(SystemBlue),
        textStyle = style.copy(
            color = Gray800,
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(),
        decorationBox = { innerTextField ->
            Row() {
                innerTextField()
            }
        }
    )
}
