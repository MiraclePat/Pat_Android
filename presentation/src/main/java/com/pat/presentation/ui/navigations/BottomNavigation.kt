package com.pat.presentation.ui.navigations

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pat.presentation.R
import com.pat.presentation.ui.proof.CertificationScreenView
import com.pat.presentation.ui.map.MapScreenView
import com.pat.presentation.ui.setting.SettingScreenView
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.White
import com.pat.presentation.ui.home.HomeScreenView

const val HOME = "HOME"
const val CERTIFICATION = "CERTIFICATION"
const val MAP = "MAP"
const val SETTING = "SETTING"

sealed class BottomNavItem(
    val title: Int, val icon: Int, val screenRoute: String
) {
    object Home : BottomNavItem(R.string.text_home, R.drawable.na_house, HOME)
    object Certification :
        BottomNavItem(R.string.text_certification, R.drawable.na_circle_check, CERTIFICATION)

    object Map : BottomNavItem(R.string.text_map, R.drawable.na_map, MAP)
    object Setting : BottomNavItem(R.string.text_setting, R.drawable.na_user, SETTING)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screenRoute) {
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreenView()
        }
        composable(BottomNavItem.Certification.screenRoute) {
            CertificationScreenView()
        }
        composable(BottomNavItem.Map.screenRoute) {
            MapScreenView()
        }
        composable(BottomNavItem.Setting.screenRoute) {
            SettingScreenView()
        }
    }
}


@Composable
fun BottomNavi(navController: NavHostController) {
    val items = listOf<BottomNavItem>(
        BottomNavItem.Home,
        BottomNavItem.Certification,
        BottomNavItem.Map,
        BottomNavItem.Setting,
    )

    BottomNavigation(
        backgroundColor = White,
        contentColor = PrimaryMain
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val iconColor = if (currentRoute == item.screenRoute) PrimaryMain else Gray400
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                        tint = iconColor
                    )
                },
                label = { Text(stringResource(id = item.title), fontSize = 9.sp) },
                selectedContentColor = PrimaryMain,
                unselectedContentColor = Gray400,
                selected = currentRoute == item.screenRoute,
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}