package com.pat.presentation.ui.common

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


// 어쩌다가 공부하게 되어서 만들어 놓음,, 추후 사용할수도 있어서 파일만 만들어 놓습니다
@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    value: String,
    setShowDialog: (Boolean) -> Unit,
    setValue: (String) -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            modifier = modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = Color.Gray
        ) {
            content()
        }
    }
}