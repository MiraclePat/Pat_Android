package com.pat.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pat.presentation.R
import com.pat.presentation.ui.ui.theme.MiraclePatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiraclePatTheme {
                // A surface container using the 'background' color from the theme
                Surface() {
                    HomeScreenView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenView() {
//    val content by viewModel.content.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            HomeTopBar(
                searchTextField = {
                    SearchTextField(
                        value = "",
//                        value = content,
                        hint = "어떤 팟을 찾고계신가요?",
                        onValueChange = {},
                    )
                },
                addButton = { BarIcon(onclick = {}, imageVector = Icons.Rounded.Add) },
                alarmButton = { BarIcon(onclick = {}, imageVector = Icons.Rounded.Notifications) })
        },
        bottomBar = {
            BottomAppBar() {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            HomeMyPat()
            CategoryPat()
            HatPat()
            RecentPat()
        }
    }
}


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

@Composable
fun HatPat(modifier: Modifier = Modifier) {
    Column(modifier.padding(10.dp)) {
        Text(text = "지금 가장 핫한 팟에 동참해보세요!", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.size(10.dp))
        LazyRow() {
            items(5) {
                Pats()
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
                Pats()
                Spacer(Modifier.size(10.dp))
            }
        }
    }
}

@Composable
fun Pats(
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

@Composable
fun CategoryPat(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Row(modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        MapButton(text = "관악구")
        Spacer(Modifier.size(10.dp))
        Row(
            modifier
                .padding(10.dp)
                .horizontalScroll(scrollState)
        ) {
            CategoryButtonList()
        }
    }
}

@Composable
fun MapButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonColor: Color = Color.White,
    text: String,
    textColor: Color = Color.Black,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onPressing: () -> Unit = {},
    onPressed: () -> Unit = {}
) {

    val isPressed by interactionSource.collectIsPressedAsState()

    val verticalPadding by animateDpAsState(
        targetValue = if (isPressed) 13.dp else 16.dp, label = ""
    )

    LaunchedEffect(isPressed) {
        if (isPressed) onPressing()
        else onPressed()
    }

    Button(
        modifier = modifier,
        interactionSource = interactionSource,
        contentPadding = PaddingValues(vertical = verticalPadding),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        enabled = enabled,
        onClick = {
            onClick()
        },
    ) {
        Row() {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                style = MaterialTheme.typography.bodyMedium,
                text = text,
                color = textColor
            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

@Composable
fun CategoryButtonList() {
    val categories = listOf<String>("전체", "환경", "건강", "식습관", "취미", "생활")
    categories.forEach { category ->
        CategoryButton(text = category)
        Spacer(Modifier.size(10.dp))
    }
}


@Composable
fun CategoryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonColor: Color = Color.Black,
    text: String,
    textColor: Color = Color.White,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onPressing: () -> Unit = {},
    onPressed: () -> Unit = {}
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    LaunchedEffect(isPressed) {
        if (isPressed) onPressing()
        else onPressed()
    }

    Button(
        modifier = modifier.height(45.dp),
        interactionSource = interactionSource,
        shape = RoundedCornerShape(22.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        enabled = enabled,
        onClick = {
            onClick()
        },
    ) {
        Text(
            modifier = modifier,
            style = MaterialTheme.typography.bodyMedium,
            text = text,
            color = textColor
        )
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

//    Box(
//        modifier
//            .background(Color.Gray)
//            .width(280.dp)
//            .clickable {
//                focusRequester.requestFocus()
//            },
//
//        ) {
//        Row(
//            modifier.padding(10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                imageVector = Icons.Rounded.Search,
//                contentDescription = null
//            )
//            TextField(
//                modifier = modifier
//                    .focusRequester(focusRequester)
//                    .onFocusChanged {
//                        isFocused = it.hasFocus
//                    },
//                colors = TextFieldDefaults.textFieldColors(),
//                value = value,
//                singleLine = maxLines == 1,
//                maxLines = maxLines,
//                onValueChange = {
//                    if (it.length > maxLength) onValueChange(value)
//                    else onValueChange(it)
//                },
//                keyboardActions = keyboardActions,
//                keyboardOptions = keyboardOptions,
//
//            )
//        }
//    }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeScreenView()
}