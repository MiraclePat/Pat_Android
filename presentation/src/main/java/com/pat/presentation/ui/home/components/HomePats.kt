package com.pat.presentation.ui.home.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pat.domain.model.home.HomePatContent
import com.pat.presentation.R
import com.pat.presentation.ui.home.HomeScreenView
import com.pat.presentation.ui.theme.PrimaryMain
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
    categoryColor: Color = PrimaryMain,
    textColor: Color = Color(0xFF009D65),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onPressing: () -> Unit = {},
    onPressed: () -> Unit = {}
) {
    Column(modifier.clickable { onClick }) {
        AsyncImage(
            modifier = modifier.size(140.dp, 140.dp),
            model = "https://miraclepatbucket.s3.ap-northeast-2.amazonaws.com/repimgtest.JPG",
            contentDescription = null
        )

        Box(
            contentAlignment = Alignment.TopStart
        ) {

//            Image(
//                modifier = modifier.size(140.dp, 140.dp),
//                painter = painterResource(id = R.drawable.pat),
//                contentDescription = null
//            )
            Log.e("custom", "imageUri : $imgUri")
//            AsyncImage(
//                modifier = modifier.size(140.dp, 140.dp),
//                model = "https://miraclepatbucket.s3.ap-northeast-2.amazonaws.com/repimgtest.JPG",
//                contentDescription = null
//            )

//            GlideImage(modifier = modifier.size(140.dp, 140.dp), imageModel = { imgUri })
//            Text(
//                text = category,
//                modifier = modifier
//                    .padding(8.dp)
//                    .background(color = categoryColor, shape = RoundedCornerShape(22.dp))
//                    .width(41.dp)
//                    .height(26.dp),
//                textAlign = TextAlign.Center,
//                color = textColor
//            )
        }
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Row() {
            Icon(painter = painterResource(id = R.drawable.ic_map), contentDescription = null)
            Text(text = location)
        }
        Row() {
            Icon(painter = painterResource(id = R.drawable.ic_calendar), contentDescription = null)
            Text(text = startDate)
        }
        Row() {
            Icon(painter = painterResource(id = R.drawable.ic_user), contentDescription = null)
            Text(text = "$nowPerson / $maxPerson")
        }
    }
}

@Composable
fun Pats(modifier: Modifier = Modifier, content: List<HomePatContent>?) {
    content?.forEach {
        Log.e("custom", "$it")
    }

    Column(modifier.padding(vertical = 20.dp, horizontal = 16.dp)) {
        Text(text = "지금 가장 핫한 팟에 동참해보세요!", style = MaterialTheme.typography.titleLarge)
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
//    Spacer(Modifier.size(20.dp))
//    Column(modifier.padding(vertical = 20.dp, horizontal = 16.dp)) {
//        Text(text = "최근 개설된 팟!", style = MaterialTheme.typography.titleLarge)
//        Spacer(Modifier.size(12.dp))
//        LazyRow() {
//            items(5) {
//                HomePats()
//                Spacer(Modifier.size(10.dp))
//            }
//        }
//    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview6() {
    HomeScreenView()
}