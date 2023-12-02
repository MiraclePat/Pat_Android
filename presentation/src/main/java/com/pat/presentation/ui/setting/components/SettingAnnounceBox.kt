package com.pat.presentation.ui.setting.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.R
import com.pat.presentation.ui.common.Divider
import com.pat.presentation.ui.setting.BODY
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.Primary100
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography

@Composable
fun SettingAnnounceBox(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    body: String,
) {
    var state by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(73.dp)
            .clickable {
                state = !state
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(
                text = title,
                style = if (!state) Typography.bodySmall else Typography.displayLarge,
                color = Gray800,
                fontSize = 14.sp
            )
            Spacer(modifier = modifier.size(8.dp))
            Text(text = date, style = Typography.displaySmall, color = Gray500)
        }
        Spacer(modifier = modifier.weight(1f))
        Icon(
            modifier = modifier.size(24.dp),
            imageVector = if (!state) ImageVector.vectorResource(id = R.drawable.ic_caret_down) else ImageVector.vectorResource(
                id = R.drawable.ic_caret_up_sm
            ),
            contentDescription = "Go back"
        )
    }
    if (state) {
        Column(modifier.clickable {
            state = !state
        }) {
            Text(modifier = modifier.padding(16.dp), text = body, style = Typography.bodySmall)
            Divider(modifier.height(4.dp), color = Primary100)
        }
    }
}