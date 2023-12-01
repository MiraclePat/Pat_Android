package com.pat.presentation.ui.map.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.naver.maps.geometry.LatLng
import com.pat.presentation.R
import com.pat.presentation.ui.home.components.CategoryButton
import com.pat.presentation.ui.map.MapViewModel
import com.pat.presentation.ui.theme.*


enum class CategoryEnum(val category: Int, val backColor: Color, val textColor: Color) {
    ALL(R.string.category_all, ALLBackColor, ALLTextColor),
    ENVIRONMENT(R.string.category_environment, EnvironmentBackColor, EnvironmentTextColor),
    HEALTH(R.string.category_health, HealthBackColor, HealthTextColor),
    HABIT(R.string.category_habit, HabitBackColor, HabitTextColor),
    HOBBY(R.string.category_hobby, HobbyBackColor, HobbyTextColor),
    DAILY(R.string.category_daily, DailyBackColor, DailyTextColor),
    ETC(R.string.category_etc, EtcBackColor, EtcTextColor)
}

@Composable
fun MapCategoryBoxList(
    state: MutableState<String>,
    viewModel: MapViewModel,
    northEastCoordinate: LatLng?,
    southWestCoordinate: LatLng?
) {
    CategoryEnum.values().forEach {
        val category = stringResource(id = it.category)
        MapCategoryBox(
            category = category,
            onClick = {
                state.value = category
                viewModel.getMapPats(northEastCoordinate, southWestCoordinate, category)
            },
            isSelected = state.value == category
        )
        Spacer(Modifier.size(10.dp))
    }
}

@Composable
fun MapCategoryBox(
    modifier: Modifier = Modifier,
    category: String,
    onClick: () -> Unit = {},
    isSelected: Boolean = false,
) {
    val categoryInfo = CategoryEnum.values().first { stringResource(id = it.category) == category }

    Box(
        modifier = modifier
            .background(
                color = if (isSelected) categoryInfo.backColor else Gray100,
                shape = RoundedCornerShape(4.dp)
            )
            .width(41.dp)
            .height(26.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category,
            textAlign = TextAlign.Center,
            color = if (isSelected) categoryInfo.textColor else Gray600,
            style = Typography.labelSmall
        )
    }
}