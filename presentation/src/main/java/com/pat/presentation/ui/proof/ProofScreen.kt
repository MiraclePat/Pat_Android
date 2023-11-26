package com.pat.presentation.ui.proof

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.proof.ProofContent
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBox
import com.pat.presentation.ui.common.SimpleTextView
import com.pat.presentation.ui.common.setUnderLine
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProofScreenView(modifier: Modifier = Modifier) {
    val patStatusList = listOf("참여중인 팟", "참여예정 팟", "완료한 팟", "개설한 팟")
    val patState = remember { mutableStateOf(patStatusList.first()) }
    val scrollState = rememberScrollState()

    Column(
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 33.dp, end = 16.dp, start = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            patStatusList.forEach { patStatus ->
                PatStatus(patState = patState, text = patStatus)
            }
        }
        Spacer(modifier = modifier.padding(bottom = 28.dp))
        ParticipatePat()
        ParticipatePat()
        ParticipatePat()
    }
}

@Composable
fun PatStatus(modifier: Modifier = Modifier, patState: MutableState<String>, text: String) {
    Box(modifier.clickable {
        patState.value = text
    }) {
        Text(
            modifier = if (patState.value == text) setUnderLine else modifier,
            text = text,
            style = Typography.titleLarge,
            color = if (patState.value == text) Gray800 else Gray400
        )
    }
}

@Composable
fun ParticipatePat(
    modifier: Modifier = Modifier,
) {
    var spreadState by remember { mutableStateOf(false) }
    val spreadHeight = if (!spreadState) 58.dp else 186.dp

    @Composable
    fun simpleTextView(
        text: String,
        vectorResource: Int,
    ) {
        SimpleTextView(
            text = text,
            vectorResource = vectorResource,
            style = Typography.labelSmall.copy(
                color = Gray600
            ),
            iconSize = 16.dp
        )
        Spacer(modifier = modifier.padding(bottom = 8.dp))
    }
    Column(
        modifier
            .fillMaxWidth()
            .height(spreadHeight)
            .border(1.dp, Gray200, RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp)
            .clickable { spreadState = !spreadState },
        verticalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CategoryBox(category = "환경", isSelected = true)
            Spacer(modifier = modifier.padding(start = 11.dp))
            Text("강아지 산책", style = Typography.titleLarge, color = Black)
            Spacer(modifier = modifier.padding(start = 8.dp))
            Text("매일 인증", style = Typography.bodySmall, color = Gray600)
            Spacer(modifier.weight(1f))
            Icon(
                modifier = modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(
                    id = if (!spreadState) R.drawable.ic_caret_down else R.drawable.ic_caret_up_sm
                ),
                contentDescription = null,
                tint = Gray800
            )
        }
        if (spreadState) {
            Spacer(modifier.padding(top = 16.dp))
            Row() {
                GlideImage(
                    modifier = modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    imageModel = { "https://miraclepatbucket.s3.ap-northeast-2.amazonaws.com/repimgtest.JPG" }
                )
                Column(modifier.padding(start = 16.dp)) {
                    simpleTextView(
                        text = "서울시 관악구 신사동",
                        vectorResource = R.drawable.ic_map,
                    )
                    simpleTextView(
                        text = "11월 9일(목) 시작",
                        vectorResource = R.drawable.ic_calendar,
                    )
                    simpleTextView(
                        text = "8명 / 20명",
                        vectorResource = R.drawable.ic_user,
                    )
                    Row(
                        modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .border(1.dp, PrimaryMain, RoundedCornerShape(100.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("인증하기", style = Typography.bodySmall, color = PrimaryMain)
                        Icon(
                            modifier = modifier.size(16.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_caret_down),
                            contentDescription = null,
                            tint = PrimaryMain
                        )
                    }
                }
            }
        }
    }
    Spacer(modifier.padding(bottom = 14.dp))
}