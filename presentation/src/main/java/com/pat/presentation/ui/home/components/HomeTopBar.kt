package com.pat.presentation.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pat.presentation.ui.home.HomeScreenView
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.Typography

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    searchTextField: @Composable () -> Unit = {},
    addButton: @Composable () -> Unit = {},
    alarmButton: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 22.dp)
            .height(38.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Box(modifier.weight(1.0F)) {
            searchTextField()
        }
        Spacer(modifier = modifier.padding(8.dp))
        addButton()
        Spacer(modifier = modifier.padding(8.dp))
        alarmButton()
    }
}


@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    state: MutableState<String>
) {
//    var text by rememberSaveable { mutableStateOf("") }

    BasicTextField(
        modifier = modifier
            .background(Gray100)
            .fillMaxWidth()
            .height(36.dp),
        value = state.value,
        onValueChange = {
            state.value = it
        },
        singleLine = true,
        cursorBrush = SolidColor(Gray400),
        textStyle = Typography.labelMedium.copy(
            color = Gray400,
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier
                    .clip(RoundedCornerShape(4.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = Gray400
                )
                Box(
                    Modifier
                        .padding(horizontal = 10.dp)
                ) {
                    if (state.value.isEmpty()) {
                        Text(
                            "어떤 팟을 찾고계신가요?",
                            style = Typography.labelMedium,
                            color = Gray500
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

