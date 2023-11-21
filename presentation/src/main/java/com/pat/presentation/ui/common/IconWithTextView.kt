package com.pat.presentation.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography


@Composable
fun IconWithTextView(
    title: String,
    iconResource: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                BorderStroke(1.dp, Gray200),
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconResource), contentDescription = null, tint = PrimaryMain
            )

            Spacer(modifier = modifier.width(8.dp))
            Text(
                text = title, modifier = modifier.padding(3.dp), style = Typography.displaySmall,
                fontSize = 14.sp, color = Gray800
            )
        }
    }
}