package com.pat.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
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
import com.pat.presentation.ui.screens.HomeScreenView
import com.pat.presentation.ui.screens.MainScreenView
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.PrimaryMain

@Composable
fun HomeMyPat(modifier: Modifier = Modifier) {
    Row(modifier.padding(horizontal = 26.dp, vertical = 40.dp)) {
        Column() {
            Text(
                text = stringResource(id = R.string.home_empty_pat),
                color = PrimaryMain,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.home_empty_pat2),
                color = Gray700,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier.size(20.dp))
        Image(
            modifier = modifier
                .requiredHeight(65.dp)
                .requiredWidth(124.dp),
            painter = painterResource(id = R.drawable.pat),
            contentDescription = null
        )
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    HomeScreenView()
}