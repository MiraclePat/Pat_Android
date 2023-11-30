package com.pat.presentation.ui.proof

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
import com.pat.domain.model.member.ParticipatingContent
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBox
import com.pat.presentation.ui.common.SimpleTextView
import com.pat.presentation.ui.common.setUnderLine
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ParticipatingScreenView(
    modifier: Modifier = Modifier,
    participatingViewModel: ParticipatingViewModel = hiltViewModel(),
    navController: NavController,
) {
    val patStatusList = listOf("참여중인 팟", "참여예정 팟", "완료한 팟", "개설한 팟")
    val patState = remember { mutableStateOf(patStatusList.first()) }
    val scrollState = rememberScrollState()
    val uiState by participatingViewModel.uiState.collectAsState()


    LaunchedEffect(uiState.content) {
        Logger.t("MainTest").i("${uiState.content}")
    }

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

        val buttonText: (String) -> (String) = { state ->
            when (state) {
                "SCHEDULED" -> "상세보기"
                "IN_PROGRESS" -> "인증하기"
                "COMPLETED" -> "인증완료"
                else -> "예외 발생"
            }
        }
        when (patState.value) {
            "참여중인 팟" -> {
                participatingViewModel.getInProgress()
                uiState.content?.forEach { participatingContent ->
                    ParticipatePat(
                        content = participatingContent,
                        onClick = {
                            navController.navigate("participatingDetail/${participatingContent.patId}")
                        },
                        buttonText = buttonText(participatingContent.state),
                        color = if (participatingContent.state == "COMPLETED") Gray300 else PrimaryMain,
                        buttonClick = {
                            // TODO 인증하기 바텀시트
                        }
                    )
                }
            }

            "참여예정 팟" -> {
                participatingViewModel.getScheduled()
                uiState.content?.forEach { participatingContent ->
                    ParticipatePat(
                        content = participatingContent,
                        onClick = { navController.navigate("participatingDetail/${participatingContent.patId}") },
                        buttonText = "상세보기"
                    )
                }
            }

            "완료한 팟" -> {
                participatingViewModel.getCompleted()
                uiState.content?.forEach { participatingContent ->
                    ParticipatePat(
                        content = participatingContent,
                        onClick = { navController.navigate("participatingDetail/${participatingContent.patId}") },
                        buttonText = "종료된 팟",
                        color = Gray300
                    )
                }
            }

            "개설한 팟" -> {
                participatingViewModel.getOpenPats()
                uiState.content?.forEach { participatingContent ->
                    ParticipatePat(
                        content = participatingContent,
                        onClick = { navController.navigate("participatingDetail/${participatingContent.patId}") },
                        buttonText = buttonText(participatingContent.state),
                        color = if (participatingContent.state == "COMPLETED") Gray300 else PrimaryMain,
                        buttonClick = {
                            // TODO 인증하기 바텀시트
                        }
                    )
                }
            }
        }
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
    content: ParticipatingContent,
    onClick: () -> Unit,
    buttonClick: () -> Unit = onClick,
    buttonText: String,
    color: Color = PrimaryMain
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
            CategoryBox(category = content.category, isSelected = true)
            Spacer(modifier = modifier.padding(start = 11.dp))
            Text(content.patName, style = Typography.titleLarge, color = Black)
            Spacer(modifier = modifier.padding(start = 8.dp))
            Text(content.days, style = Typography.bodySmall, color = Gray600)
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
            Row(modifier.clickable {
                onClick()
            }) {
                GlideImage(
                    modifier = modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    imageModel = { content.repImg }
                )
                Column(modifier.padding(start = 16.dp)) {
                    simpleTextView(
                        text = content.location.ifEmpty { "어디서나 가능" },
                        vectorResource = R.drawable.ic_map,
                    )
                    simpleTextView(
                        text = content.startDate,
                        vectorResource = R.drawable.ic_calendar,
                    )
                    simpleTextView(
                        text = "${content.nowPerson}명 / ${content.maxPerson}명",
                        vectorResource = R.drawable.ic_user,
                    )
                    Row(
                        modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .border(1.dp, color, RoundedCornerShape(100.dp))
                            .clickable { buttonClick() },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(buttonText, style = Typography.bodySmall, color = color)
                        Icon(
                            modifier = modifier.size(16.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_caret_down),
                            contentDescription = null,
                            tint = color
                        )
                    }
                }
            }
        }
    }
    Spacer(modifier.padding(bottom = 14.dp))
}