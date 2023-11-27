package com.pat.presentation.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.R
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.PrimaryMain

@Composable
fun CheckBoxView(
    modifier: Modifier = Modifier,
    checked: MutableState<Boolean> = remember { mutableStateOf(true) },
    text: String,
    isRealtime: Boolean = false
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            modifier = modifier
                .size(18.dp),
            checked = checked.value,
            colors = CheckboxDefaults.colors(
                checkedColor = PrimaryMain,
                uncheckedColor = Gray300
            ),
            onCheckedChange = {
                if (isRealtime) checked.value = true
                else checked.value = it
            })
        Spacer(modifier = modifier.padding(6.dp))
        ClickableText(
            text = AnnotatedString(text),
            onClick = {
                if (isRealtime) checked.value = true
                else checked.value = !checked.value
            },
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Gray800,
            )
        )
    }
}