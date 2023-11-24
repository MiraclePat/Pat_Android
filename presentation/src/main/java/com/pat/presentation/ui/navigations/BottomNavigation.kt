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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.pat.presentation.R
import com.pat.presentation.ui.home.HomeScreenView
import com.pat.presentation.ui.map.MapScreenView
import com.pat.presentation.ui.pat.PatDetailView
import com.pat.presentation.ui.post.PostScreenView
import com.pat.presentation.ui.proof.ProofScreenView
import com.pat.presentation.ui.setting.SettingScreenView
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White

const val HOME = "HOME"
const val CERTIFICATION = "CERTIFICATION"
const val MAP = "MAP"
const val SETTING = "SETTING"
const val POST = "POST"
const val POST_DETAIL = "POST_DETAIL"

sealed class BottomNavItem(
    val title: Int, val icon: Int, val iconFill: Int, val screenRoute: String,
) {
    object Home :
        BottomNavItem(R.string.text_home, R.drawable.na_house, R.drawable.na_house_fill, HOME)

    object Certification :
        BottomNavItem(
            R.string.text_certification,
            R.drawable.na_circle_check,
            R.drawable.na_circle_check_fill,
            CERTIFICATION
        )

    object Map : BottomNavItem(R.string.text_map, R.drawable.na_map, R.drawable.na_map_fill, MAP)
    object Setting :
        BottomNavItem(R.string.text_setting, R.drawable.na_user, R.drawable.na_user_fill, SETTING)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screenRoute) {
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreenView(
                navController = navController,
                onNavigateToPost = { navController.navigate(POST) })
        }
        composable(BottomNavItem.Certification.screenRoute) {
            ProofScreenView()
        }
        composable(BottomNavItem.Map.screenRoute) {
            MapScreenView()
        }
        composable(BottomNavItem.Setting.screenRoute) {
            SettingScreenView()
        }
        composable(POST) {
            PostScreenView(onNavigateToHome = { navController.popBackStack() })
        }
        composable(
            route = "patDetail/{patId}",
            arguments = listOf(
                navArgument("patId"){
                    type = NavType.LongType
                    defaultValue = -1
                }
            )){

            PatDetailView()
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
            val textColor = if (currentRoute == item.screenRoute) PrimaryMain else Gray400
            val iconImage = if (currentRoute == item.screenRoute) item.iconFill else item.icon
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(iconImage),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                        tint = Color.Unspecified
                    )
                },
                label = {
                    Text(
                        stringResource(id = item.title),
                        style = Typography.titleLarge,
                        fontSize = 12.sp,
                        color = textColor
                    )
                },
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