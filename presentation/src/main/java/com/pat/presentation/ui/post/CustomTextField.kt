package com.pat.presentation.ui.post

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    placeholderText: String = "Placeholder",
    style: TextStyle = Typography.labelMedium
) {
    var text by rememberSaveable { mutableStateOf("") }
    BasicTextField(modifier = modifier
        .background(White)
        .fillMaxWidth()
        .height(46.dp)
        .border(2.dp, Gray200),
        value = text,
        onValueChange = {
            text = it
        },
        singleLine = true,
        cursorBrush = SolidColor(Gray400),
        textStyle = style.copy(
            color = Gray400,
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                ) {
                    if (text.isEmpty()) Text(
                        placeholderText,
                        style = style,
                        color = Gray400
                    )
                    innerTextField()
                }
            }
        }
    )
}