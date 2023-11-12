package com.pat.presentation.ui.navigations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    searchTextField: @Composable () -> Unit = {},
    addButton: @Composable () -> Unit = {},
    alarmButton: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            searchTextField()
            addButton()
            alarmButton()
        }
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
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused = it.hasFocus
            },
        colors = TextFieldDefaults.textFieldColors(),
        value = value,
        placeholder = { Text(hint) },
        singleLine = maxLines == 1,
        maxLines = maxLines,
        onValueChange = {
            if (it.length > maxLength) onValueChange(value)
            else onValueChange(it)
        },
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        leadingIcon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = null) },
    )
}

@Composable
fun BarIcon(
    onclick: () -> Unit = {},
    imageVector: ImageVector,
    contentDescription: String? = null
) {
    IconButton(onClick = onclick) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}