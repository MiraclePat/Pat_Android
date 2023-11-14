package com.pat.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.R
import com.pat.presentation.ui.ui.theme.MiraclePatTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class PostDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiraclePatTheme {
                PostDetailView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailView(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = {
                        Text(
                            text = "강아지 산책",
                            fontSize = 14.sp,
                            modifier = modifier.fillMaxWidth(),
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Go back"
                            )
                        }
                    },
                    actions = {
                    },
                    scrollBehavior = scrollBehavior,
                )
            }
        ) { values ->
            Column(
                modifier = modifier
                    .padding(values)
            ) {
                PostDetailScreen()
            }
        }
    }
}

@Composable
fun PostDetailScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column() {
            Image(
                modifier = modifier
                    .fillMaxWidth()
                    .height(160.dp),
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null
            )
            Column(modifier = modifier.padding(10.dp)) {
                Row() {
                    Box(
                        modifier = modifier
                            .width(40.dp)
                            .height(35.dp)
                            .background(
                                color = Color.Red,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text(
                            text = "일상",
                            modifier = modifier
                                .padding(8.dp)
                                .align(Alignment.Center),
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                    Text(
                        text = "강아지 산책",
                        fontSize = 20.sp,
                        modifier = modifier.padding(3.dp)
                    )
                }
                Text("서울시 관악구 신사동", modifier = modifier.padding(3.dp))
                Text("11월 13일(월) - 11월 27일(월) 총 2주간", modifier = modifier.padding(3.dp))
                Text("현재 8명 / 20명", modifier = modifier.padding(3.dp)) //TODO GETSTRING
                Spacer(modifier.size(20.dp))

                Text("인증사진", fontSize = 16.sp, modifier = modifier.padding(3.dp))
                PatPhotos()
                Spacer(modifier.size(20.dp))

                Text(
                    "팟 정보", fontSize = 16.sp, modifier = modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                )
                CustomText("강아지와 주 2회 산책해요",painterResource(id = R.drawable.ic_add))

                Spacer(modifier.size(20.dp))

                Text("인증 가능 시간", fontSize = 16.sp, modifier = modifier.padding(3.dp))
//                CustomText("오전 11시부터 오후 11시까지", R.drawable.ic_alram)

                Spacer(modifier.size(20.dp))
                Text("인증 빈도", fontSize = 16.sp, modifier = modifier.padding(3.dp))
                ProofFrequencyList()

                Spacer(modifier.size(20.dp))
                Text("시작일-종료일", fontSize = 16.sp, modifier = modifier.padding(3.dp))

                Spacer(modifier.size(20.dp))
                Text("인증 방법", fontSize = 16.sp, modifier = modifier.padding(3.dp))
                Box(modifier.padding(10.dp)) {
//                    CustomText("목줄을 찬 반려동물이 바깥 풍경과 함꼐 나오도록 사진을 찍어주세요.", "")
                }

                Spacer(modifier.size(20.dp))
                Text("인증 수단", fontSize = 16.sp, modifier = modifier.padding(3.dp))
//                CustomText("실시간 촬영", "")
//                CustomText("갤러리에서 사진 가져오기", "")

                Spacer(modifier.size(44.dp))
                ParticipatePatButton()
            }
        }
    }
}

@Composable
fun CustomText(
    title: String,
    painter: Painter,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Icon(
                painter = painter, contentDescription = null
            )

            Spacer(modifier = modifier.width(8.dp))
            Text(text = title, modifier = modifier.padding(3.dp))
        }
    }
}

@Composable
fun ParticipatePatButton(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable(onClick = {})
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline,
                            ),
                        ) {
                            Text("팟 참여하기")
                        }
                    },
                    color = Color.Red,
                    fontSize = 16.sp,
                )
            }
        }
    }
}


@Composable
fun PatPhotos(modifier: Modifier = Modifier) {
    Row() {
        LazyRow() {
            items(5) {
                PreviewPhotos()
            }
        }
    }
}

@Composable
fun PreviewPhotos(modifier: Modifier = Modifier) {
    Image(
        modifier = Modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.ic_add),
        contentDescription = null
    )
}

@Composable
fun ProofFrequencyList() {
    val categories = listOf<String>("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")
    LazyRow() {
        items(5) {
            categories.forEach { category ->
                ProofFrequencyButton(text = category)
            }
        }
    }
}


@Composable
fun ProofFrequencyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonColor: Color = Color.Black,
    text: String,
    textColor: Color = Color.White,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onPressing: () -> Unit = {},
    onPressed: () -> Unit = {}
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    val verticalPadding by animateDpAsState(
        targetValue = if (isPressed) 13.dp else 16.dp, label = ""
    )

    LaunchedEffect(isPressed) {
        if (isPressed) onPressing()
        else onPressed()
    }

    Button(
        modifier = modifier,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(vertical = verticalPadding),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        enabled = enabled,
        onClick = {
            onClick()
        },
    ) {
        Box(
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyMedium,
                text = text,
                color = textColor
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PostDetailView()
}