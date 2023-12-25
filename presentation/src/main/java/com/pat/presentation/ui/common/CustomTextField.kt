package com.pat.presentation.ui.common


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.SystemBlue
import com.pat.presentation.ui.theme.Typography


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    placeholderText: String,
    style: TextStyle = Typography.labelMedium,
    maxLength: Int,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    state: MutableState<String> = rememberSaveable {
        mutableStateOf("")
    },
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColor = if (!isFocused) Gray200 else SystemBlue

    BasicTextField(modifier = modifier
        .fillMaxWidth()
        .heightIn(1.dp, Dp.Infinity)
        .clip(RoundedCornerShape(4.dp))
        .onFocusChanged {
            isFocused = it.hasFocus
        }
        .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
        value = state.value,
        onValueChange = {
            if (it.length <= maxLength) state.value = it
        },
        cursorBrush = SolidColor(SystemBlue),
        textStyle = style.copy(color = Gray800),
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        decorationBox = { innerTextField ->
            Row(
                modifier
                    .padding(horizontal = 10.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(4.dp)),
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp)),
                ) {
                    if (!isFocused && state.value == "") Text(
                        placeholderText,
                        style = style,
                        color = Gray400
                    ) else {
                        innerTextField()
                    }
                }
            }
        }
    )
}