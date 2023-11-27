package com.pat.presentation.ui.post.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.presentation.ui.post.PostViewModel
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography

@Composable
fun SearchResultList(
    places: List<PlaceDetailInfo>,
    placeText: MutableState<String>,
    postViewModel: PostViewModel,
    modifier: Modifier = Modifier,
) {
    places.forEach{ place ->
        SearchPlace(
            title = place.title,
            address = place.address,
            onClick = {
                placeText.value = place.title.toString()
                postViewModel.selectPlace(place)
            },
            isSelected = placeText.value == place.title,
        )
        Spacer(modifier.padding(bottom = 10.dp))

    }

}

@Composable
fun SearchPlace(
    title: String?,
    address: String?,
    onClick: () -> Unit,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val borderColor = if (isSelected) PrimaryMain else Gray200

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp)
            .border(
                BorderStroke(1.dp, borderColor),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onClick() },

        ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title.toString(), modifier = modifier.padding(3.dp), style = Typography.bodySmall,
                fontSize = 14.sp, color = Gray800
            )
            Text(
                text = address.toString(), modifier = modifier.padding(3.dp), style = Typography.labelSmall,
                fontSize = 12.sp, color = Gray600
            )
        }


    }

}