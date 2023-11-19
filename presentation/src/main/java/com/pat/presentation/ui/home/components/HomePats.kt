package com.pat.presentation.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pat.domain.model.home.HomePatContent
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBox
import com.pat.presentation.ui.theme.Typography
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomePats(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    title: String = "강아지 산책",
    location: String = "서울시 관악구 신사동",
    startDate: String = "12.5(금) 시작",
    imgUri: String,
    nowPerson: Int = 8,
    maxPerson: Int = 10,
    category: String = "환경",
    categoryColor: Color = Color(0xFFE2FFEC),
    textColor: Color = Color(0xFF009D65),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onPressed: () -> Unit = {}
) {
    Column(
        modifier
            .clickable { onClick() }) {
        Box() {
            GlideImage(
                modifier = modifier
                    .size(140.dp, 140.dp)
                    .clip(RoundedCornerShape(12.dp)),
                imageModel = { imgUri })
            CategoryBox(
                modifier = modifier.padding(8.dp),
                category = category,
            )
        }
        Spacer(Modifier.size(10.dp))
        Text(text = title, style = Typography.labelMedium)
        Spacer(Modifier.size(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = modifier.size(12.dp, 12.dp),
                painter = painterResource(id = R.drawable.ic_map),
                contentDescription = null
            )
            Spacer(Modifier.size(4.dp))
            Text(text = location, style = Typography.labelSmall)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = modifier.size(12.dp, 12.dp),
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null
            )
            Spacer(Modifier.size(4.dp))
            Text(text = startDate, style = Typography.labelSmall)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = modifier.size(12.dp, 12.dp),
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = null
            )
            Spacer(Modifier.size(4.dp))
            Text(text = "$nowPerson / $maxPerson", style = Typography.labelSmall)
        }
    }
}

@Composable
fun Pats(modifier: Modifier = Modifier, content: List<HomePatContent>?) {
    Column(modifier.padding(vertical = 20.dp, horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.home_hot_pat_title),
            style = Typography.titleLarge
        )
        Spacer(Modifier.size(12.dp))
        LazyRow() {
            if (!content.isNullOrEmpty()) {
                items(content) { pat ->
                    HomePats(
                        title = pat.patName, category = pat.category, nowPerson = pat.nowPerson,
                        maxPerson = pat.maxPerson, startDate = pat.startDate, imgUri = pat.repImg
                    )
                    Spacer(Modifier.size(10.dp))
                }
            } else {
                // TODO 팟이 아무것도 없을 때
            }
        }
    }
    Spacer(Modifier.size(20.dp))
    Column(modifier.padding(vertical = 20.dp, horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.home_recent_pat_title),
            style = Typography.titleLarge
        )
        Spacer(Modifier.size(12.dp))
        LazyRow() {
            if (!content.isNullOrEmpty()) {
                items(content) { homePat ->
                    HomePats(
                        title = homePat.patName,
                        category = homePat.category,
                        nowPerson = homePat.nowPerson,
                        maxPerson = homePat.maxPerson,
                        startDate = homePat.startDate,
                        imgUri = homePat.repImg
                    )
                    Spacer(Modifier.size(10.dp))
                }
            } else {
                // TODO 팟이 아무것도 없을 때
            }
        }
    }
}
