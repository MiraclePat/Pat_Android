package com.pat.presentation.ui.common


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pat.presentation.R
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.Gray900
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.SpanStyleType
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun WheelTimePickerView(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    timeState: MutableState<String>
) {
    val amState = remember { mutableStateOf(true) }
    val hourPickerState = rememberPickerState()

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = modifier
                .height(324.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
                    .clickable {
                        onDismiss()
                    },
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    tint = Gray500,
                )
            }
            Text(buildAnnotatedString {
                withStyle(style = SpanStyleType) {
                    append("인증 ")
                }
                withStyle(
                    style = SpanStyleType.copy(
                        color = PrimaryMain
                    )
                ) {
                    append("시작 시간")
                }
                withStyle(style = SpanStyleType) {
                    append("을 선택해주세요.")
                }
            })
            Spacer(modifier = modifier.padding(bottom = 8.dp))
            Text("1시간 단위로만 선택 가능해요", style = Typography.bodySmall, color = Gray600)

            PickerExample(hourPickerState = hourPickerState, amState = amState)
            Box(
                modifier
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Gray200), shape = RoundedCornerShape(100.dp))
                    .clickable {
                        val am = if (amState.value) "오전" else "오후"
                        timeState.value = "$am ${hourPickerState.selectedItem}시"
                        onDismiss()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("선택 완료", style = Typography.labelMedium, color = PrimaryMain)
            }
        }
    }
}


@Composable
fun PickerExample(
    modifier: Modifier = Modifier,
    hourPickerState: PickerState,
    amState: MutableState<Boolean>
) {
    val hour = remember { (1..12).map { it.toString().padStart(2, '0') } }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(126.dp)
            .padding(top = 20.dp, start = 5.dp, end = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier
                    .width(57.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(if (amState.value) PrimaryMain else White)
                    .clickable {
                        amState.value = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "오전", style = Typography.labelMedium.copy(
                        fontSize = 14.sp,
                        color = if (amState.value) White else Gray600
                    )
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Box(
                modifier
                    .width(57.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(if (!amState.value) PrimaryMain else White)
                    .clickable {
                        amState.value = false
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "오후", style = Typography.labelMedium.copy(
                        fontSize = 14.sp,
                        color = if (!amState.value) White else Gray600
                    )
                )
            }
        }
        Box(
            modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(Gray200)
        )
        Picker(
            modifier = modifier
                .weight(1f),
            state = hourPickerState,
            items = hour,
            textModifier = modifier,
            textStyle = Typography.displayLarge.copy(
                color = Gray800
            ),
        )
        Box(
            modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(Gray200)
        )
        Box(
            modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "00", style = Typography.displayLarge.copy(
                    color = Gray800
                )
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Picker(
    items: List<String>,
    state: PickerState = rememberPickerState(),
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    textModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    dividerColor: Color = LocalContentColor.current,
    listScrollCount: Int = Integer.MAX_VALUE
) {

    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex =
        listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex

    fun getItem(index: Int) = items[index % items.size]

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightPixels = remember { mutableIntStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.intValue)

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item ->
                state.selectedItem = item
            }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(itemHeightDp * visibleItemsCount)
                .fadingEdge(fadingEdgeGradient)
        ) {
            items(listScrollCount) { index ->
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = getItem(index),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = textStyle,
                        modifier = Modifier
                            .onSizeChanged { size -> itemHeightPixels.intValue = size.height }
                            .then(textModifier)
                    )
                }
            }
        }
    }

}

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }


@Composable
fun rememberPickerState() = remember { PickerState() }

class PickerState {
    var selectedItem by mutableStateOf("")
}