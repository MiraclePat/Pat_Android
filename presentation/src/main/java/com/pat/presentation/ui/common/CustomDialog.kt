package com.pat.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pat.presentation.R
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray900
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White


@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    okRequest: () -> Unit,
    state: MutableState<Boolean>,
    message: String,
    okMessage: String,
    cancelMessage: String
) {
    Dialog(onDismissRequest = { state.value = false }) {
        Box(
            modifier = modifier
                .requiredHeight(199.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(White),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier.padding(vertical = 7.dp))
                Box(
                    modifier
                        .size(42.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = modifier,
                        painter = painterResource(id = R.drawable.ic_warning),
                        contentDescription = "warning",
                        tint = Color.Unspecified
                    )
                }
                Spacer(modifier.padding(vertical = 8.dp))
                Text(
                    text = message,
                    style = Typography.titleLarge,
                    color = Gray900,
                    fontSize = 18.sp
                )
                Spacer(modifier.padding(vertical = 12.dp))

                Row(modifier.padding(horizontal = 24.dp)) {
                    Box(
                        modifier
                            .weight(1f)
                            .height(46.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(PrimaryMain)
                            .clickable {
                                state.value = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = cancelMessage, style = Typography.labelMedium, color = White)
                    }
                    Spacer(modifier.padding(horizontal = 12.dp))
                    Box(
                        modifier
                            .weight(1f)
                            .height(46.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .border(1.dp, color = Gray300, RoundedCornerShape(100.dp))
                            .background(White)
                            .clickable {
                                state.value = false
                                okRequest()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = okMessage , style = Typography.labelMedium, color = Gray600)
                    }
                }
            }
        }
    }
}
