package com.pat.presentation.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pat.domain.model.pat.HomeBannerContent
import com.pat.presentation.R
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography

@Composable
fun HomeMyPat(
    modifier: Modifier = Modifier,
    content: HomeBannerContent?,
    navController: NavController
) {
    if (content == null) {
        Row(modifier.padding(horizontal = 26.dp, vertical = 40.dp)) {
            Column() {
                Text(
                    text = stringResource(id = R.string.home_empty_pat),
                    style = Typography.displayLarge
                )
                Text(
                    text = stringResource(id = R.string.home_empty_pat2),
                    style = Typography.displaySmall
                )
            }
            Spacer(modifier.size(20.dp))
            Image(
                modifier = modifier
                    .height(65.dp)
                    .width(124.dp),
                painter = painterResource(id = R.drawable.ic_character_01),
                contentDescription = null
            )
        }
    } else {
        Row(
            modifier
                .padding(horizontal = 26.dp, vertical = 25.dp)
                .clickable {
                    navController.navigate("participatingDetail/${content.patId}")
                },
            verticalAlignment = Alignment.Bottom
        ) {
            Column() {
                Text(
                    text = "오늘 인증하는 날이에요!",
                    style = Typography.labelMedium,
                    color = Gray700
                )
                Spacer(modifier.padding(bottom = 6.dp))
                Text(
                    text = "${content.patName} ${content.date}",
                    style = Typography.displayLarge,
                )
                Spacer(modifier.padding(bottom = 14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "바로 인증하기",
                        style = Typography.labelMedium,
                        color = PrimaryMain
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_caret_right),
                        contentDescription = null,
                        tint = PrimaryMain
                    )
                }
            }
            Spacer(modifier.size(20.dp))
            Image(
                modifier = modifier
                    .height(64.dp)
                    .width(192.dp),
                painter = painterResource(id = R.drawable.ic_character_04),
                contentDescription = null
            )
        }
    }
}
