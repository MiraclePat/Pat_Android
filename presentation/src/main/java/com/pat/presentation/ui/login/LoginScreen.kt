package com.pat.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pat.presentation.R
import com.pat.presentation.ui.common.FinalButton
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.KaKaoColor
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun LoginScreenView(
    modifier: Modifier = Modifier,
    loginState: MutableState<Boolean> = remember { mutableStateOf(false) },
    viewModel: LoginViewModel = hiltViewModel()
) {


    LaunchedEffect(Unit) {

        viewModel.event.collect {
            when (it) {
                is Event.LoginSuccess -> {
                    loginState.value = true
                }
                is Event.LoginFailed -> {
                    //TODO 로그인 실패 메세지 보내기
                }
                is Event.RegistrationSuccess ->{
                    viewModel.onLoginWithKakao()
                }
                is Event.RegistrationFailed -> {

                }

                else -> {}
            }
        }
    }

    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = modifier
                .padding(top = 183.dp, bottom = 47.dp)
                .width(168.dp)
                .height(84.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.splash_center),
            contentDescription = null
        )
        Box(
            modifier = modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .height(46.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(KaKaoColor)
                .clickable {
                   viewModel.onLoginWithKakao()
                },
        ) {
            Box(modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                Icon(
                    modifier = modifier.padding(horizontal = 16.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_kakao),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "카카오로 시작하기", style = Typography.titleLarge,
                    fontSize = 15.sp, color = Gray700
                )
            }
        }
        Spacer(modifier = modifier.size(12.dp))
        FinalButton(
            modifier = modifier.padding(horizontal = 30.dp),
            text = "비회원으로 둘러보기",
            backColor = White,
            textColor = PrimaryMain,
            stokeWidth = 1.dp,
            onClick = {
                loginState.value = true
            }
        )
        Spacer(modifier = modifier.weight(1f))
        Box(modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.splash_bottom),
                contentDescription = null
            )
        }
    }
}