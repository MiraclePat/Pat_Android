package com.pat.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pat.presentation.R


@Composable
fun HomePats(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonColor: Color = Color.White,
    title: String = "강아지 산책",
    location: String = "서울시 관악구 신사동",
    startDate: String = "12.5(금) 시작",
    curPerson: String = "8",
    maxPerson: String = "10",
    textColor: Color = Color.Black,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onPressing: () -> Unit = {},
    onPressed: () -> Unit = {}
) {
    Column() {
        Image(
            modifier = modifier.size(150.dp, 150.dp),
            imageVector = Icons.Rounded.AccountBox,
            contentDescription = null
        )
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Row() {
            Icon(imageVector = Icons.Rounded.LocationOn, contentDescription = null)
            Text(text = location)
        }
        Row() {
            Icon(imageVector = Icons.Rounded.DateRange, contentDescription = null)
            Text(text = startDate)
        }
        Row() {
            Icon(imageVector = Icons.Rounded.Person, contentDescription = null)
            Text(text = "$curPerson / $maxPerson")
        }
    }
}

@Composable
fun HatPat(modifier: Modifier = Modifier) {
    Column(modifier.padding(10.dp)) {
        Text(text = "지금 가장 핫한 팟에 동참해보세요!", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.size(10.dp))
        LazyRow() {
            items(5) {
                HomePats()
                Spacer(Modifier.size(10.dp))
            }
        }
    }
}
@Composable
fun RecentPat(modifier: Modifier = Modifier) {
    Column(modifier.padding(10.dp)) {
        Text(text = "최근 개설된 팟!", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.size(10.dp))
        LazyRow() {
            items(5) {
                HomePats()
                Spacer(Modifier.size(10.dp))
            }
        }
    }
}



