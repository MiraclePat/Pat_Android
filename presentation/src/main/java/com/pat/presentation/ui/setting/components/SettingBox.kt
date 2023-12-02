package com.pat.presentation.ui.setting.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pat.presentation.ui.common.Divider
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.Typography

@Composable
fun SettingBox(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    setEnd: Boolean = false,
    content: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(57.dp)
            .padding(start = 16.dp, end = 21.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, style = Typography.bodySmall, color = Gray800)
        if (setEnd) {
            Spacer(modifier = modifier.weight(1f))
        } else {
            Spacer(modifier = modifier.size(6.dp))
        }
        content()
    }
    Divider(modifier.height(2.dp))
}