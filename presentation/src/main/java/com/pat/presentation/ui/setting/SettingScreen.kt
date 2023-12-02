package com.pat.presentation.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.R
import com.pat.presentation.ui.common.Divider
import com.pat.presentation.ui.common.setUnderLine
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.Primary100
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography


@Composable
fun SettingScreenView() {
    SettingScreenBody()
}

@Composable
fun SettingScreenBody(modifier: Modifier = Modifier) {
    val tempLoginState = true


    @Composable
    fun patCount(modifier: Modifier = Modifier, text: String, count: Int) {
        Column(
            modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text, style = Typography.bodySmall, color = Gray600)
            Spacer(modifier = modifier.size(13.dp))
            Text("${count}개", style = Typography.titleLarge, color = PrimaryMain, fontSize = 18.sp)
        }
    }

    @Composable
    fun setting(
        modifier: Modifier = Modifier,
        text: String,
        onClick: () -> Unit = {},
        content: @Composable () -> Unit = {}
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(57.dp)
                .padding(start = 16.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, style = Typography.bodySmall, color = Gray800)
            Spacer(modifier = modifier.size(6.dp))
            content()
        }
        Divider(modifier.height(2.dp))
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
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(
                modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(1.dp, Gray100, CircleShape),
            ) {
                // TODO Glide Image Url
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("유저 닉네임", style = Typography.titleLarge, color = Gray800)
                Icon(
                    modifier = modifier.padding(start = 2.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_pencil),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
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
            patCount(modifier = modifier.weight(1f), text = "완료한 팟", count = 3)
            Box(
                modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(Primary100)
            )
            patCount(modifier = modifier.weight(1f), text = "개설한 팟", count = 2)
        }
        Divider(modifier = modifier.padding(top = 24.dp))
        setting(text = "계정 설정", onClick = {})
        setting(text = "알림 정보 설정", onClick = {})
        setting(text = "공지사항", onClick = {})
        setting(
            text = "버전 정보",
            content = { Text("1.0", style = Typography.bodySmall, color = Gray500) })
    }
}
