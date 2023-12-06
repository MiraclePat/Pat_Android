package com.pat.presentation.ui.post.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.R
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.GreenText
import com.pat.presentation.ui.theme.RedBack
import com.pat.presentation.ui.theme.StarColor
import com.pat.presentation.ui.theme.Typography

@Composable
fun PostTitleBox(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String = "",
    essential: Boolean = true,
    approve: Boolean = false,
    content: @Composable () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = title, style = Typography.titleLarge)
        if (essential) {
            Spacer(modifier = modifier.size(4.dp))
            Text(text = "*", style = Typography.titleLarge, color = StarColor)
        }
        if (subTitle != "") {
            Spacer(modifier = modifier.size(6.dp))
            Text(
                text = subTitle,
                style = Typography.labelMedium,
                color = Gray400,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = modifier.weight(1f))
        if (essential) {
            if (approve) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_check),
                    contentDescription = null,
                    tint = GreenText
                )

            } else {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    tint = RedBack
                )
            }
        }
    }
    Spacer(modifier = modifier.size(14.dp))
    content()
    Spacer(modifier = modifier.size(36.dp))
}