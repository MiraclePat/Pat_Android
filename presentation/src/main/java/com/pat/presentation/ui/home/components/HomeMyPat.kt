package com.pat.presentation.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.R
import com.pat.presentation.ui.home.HomeScreenView
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography

@Composable
fun HomeMyPat(modifier: Modifier = Modifier) {
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
                .requiredHeight(65.dp)
                .requiredWidth(124.dp),
            painter = painterResource(id = R.drawable.ic_character_01),
            contentDescription = null
        )
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    HomeScreenView()
}