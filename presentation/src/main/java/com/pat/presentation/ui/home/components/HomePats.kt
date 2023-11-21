package com.pat.presentation.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pat.domain.model.pat.HomePatContent
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBox
import com.pat.presentation.ui.common.SimpleTextView
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.Typography
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomePats(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String = "강아지 산책",
    location: String = "서울시 관악구 신사동",
    startDate: String = "12.5(금) 시작",
    imgUri: String,
    nowPerson: Int = 8,
    maxPerson: Int = 10,
    category: String = "환경",
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
                isSelected = true
            )
        }
        Spacer(Modifier.size(10.dp))
        Text(text = title, style = Typography.labelMedium)
        Spacer(Modifier.size(6.dp))
        SimpleTextView(text = location, vectorResource = R.drawable.ic_map, iconColor = Gray700)
        SimpleTextView(text = startDate, vectorResource = R.drawable.ic_calendar, iconColor = Gray700)
        SimpleTextView(
            text = "$nowPerson / $maxPerson",
            vectorResource = R.drawable.ic_user,
            iconColor = Gray700
        )
    }
}

@Composable
fun Pats(
    navController: NavController,
    modifier: Modifier = Modifier,
    content: List<HomePatContent>?
) {
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
                        maxPerson = pat.maxPerson, startDate = pat.startDate, imgUri = pat.repImg,
                        onClick = { navController.navigate("patDetail/${pat.patId}") }
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
                        imgUri = homePat.repImg,
                        onClick = { navController.navigate("patDetail/${homePat.patId}") }
                    )
                    Spacer(Modifier.size(10.dp))
                }
            } else {
                // TODO 팟이 아무것도 없을 때
            }
        }
    }
}
