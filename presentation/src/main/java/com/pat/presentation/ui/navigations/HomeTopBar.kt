package com.pat.presentation.ui.navigations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.ui.screens.HomeScreenView
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Gray700

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    searchTextField: @Composable () -> Unit = {},
    addButton: @Composable () -> Unit = {},
    alarmButton: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 22.dp)
            .height(46.dp)
            .fillMaxWidth(),
    ) {
        searchTextField()
        addButton()
        alarmButton()
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String = "",
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    maxLines: Int = 1,
    maxLength: Int = 10,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    focusRequester: FocusRequester = FocusRequester(),
) {
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        modifier = modifier
            .fillMaxHeight()
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused = it.hasFocus
            },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Gray100,
            unfocusedTextColor = Gray100,
            disabledTextColor = Gray100,
            focusedContainerColor = Gray100,
            unfocusedContainerColor = Gray100,
            disabledContainerColor = Gray100,
        ),
        value = value,
        placeholder = {
            if (!isFocused) {
                Text(
                    modifier = modifier.requiredHeight(26.dp),
                    text = hint,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Visible
                )
            }
        },
        singleLine = maxLines == 1,
        maxLines = maxLines,
        onValueChange = {
            if (it.length > maxLength) onValueChange(value)
            else onValueChange(it)
        },
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = Gray400
            )
        },
    )
}

@Composable
fun BarIcon(
    onclick: () -> Unit = {},
    source: Int,
    contentDescription: String? = null
) {
    IconButton(onClick = onclick) {
        Icon(
            painter = painterResource(id = source),
            contentDescription = contentDescription,
            tint = Gray700
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeScreenView()
}