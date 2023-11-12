package com.pat.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pat.presentation.R

@Composable
fun HomeMyPat(modifier: Modifier = Modifier) {
    Box(modifier.padding(horizontal = 20.dp, vertical = 30.dp)) {
        Row() {
            Column() {
                Text(text = "아직 참여 중인 팟이 없어요.", style = MaterialTheme.typography.bodyLarge)
                Text(text = "팟에 동참하고 기적을 만들어보세요!", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.size(20.dp))
            Image(
                painter = painterResource(id = R.drawable.pat),
                contentDescription = null
            )
        }
    }
}