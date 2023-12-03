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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.pat.presentation.R
import com.pat.presentation.ui.common.SettingCamera
import com.pat.presentation.ui.home.HomeScreenView
import com.pat.presentation.ui.map.MapScreenView
import com.pat.presentation.ui.pat.PatDetailView
import com.pat.presentation.ui.pat.SettingPattingCamera
import com.pat.presentation.ui.pat.PatUpdateView
import com.pat.presentation.ui.pat.PatUpdateViewModel
import com.pat.presentation.ui.pat.components.UpdateSettingCamera
import com.pat.presentation.ui.post.PostScreenView
import com.pat.presentation.ui.proof.ParticipatingScreenView
import com.pat.presentation.ui.post.PostViewModel
import com.pat.presentation.ui.proof.ProofScreenView
import com.pat.presentation.ui.proof.ProofViewModel
import com.pat.presentation.ui.setting.SettingScreenView
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import com.pat.presentation.util.*


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
    val postViewModel: PostViewModel = hiltViewModel()
    val proofViewModel: ProofViewModel = hiltViewModel()
    val updateViewModel: PatUpdateViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = BottomNavItem.Home.screenRoute) {
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreenView(
                navController = navController,
                onNavigateToPost = { navController.navigate(POST) })
        }
        composable(BottomNavItem.Certification.screenRoute) {
            ParticipatingScreenView(navController = navController)
        }
        composable(BottomNavItem.Map.screenRoute) {
            MapScreenView(navController = navController)
        }
        composable(BottomNavItem.Setting.screenRoute) {
            SettingScreenView()
        }
        composable(POST) {
            PostScreenView(
                navController = navController,
                viewModel = postViewModel
            )
        }
        composable(
            route = "patDetail/{patId}",
            arguments = listOf(
                navArgument("patId") {
                    type = NavType.LongType
                    defaultValue = -1
                }
            )) {
            PatDetailView(
                navController = navController)
        }

        composable("camera/{bitmapType}/{updateState}/{originalIdx}") { backStackEntry ->
            val bitmapType = backStackEntry.arguments?.getString("bitmapType")
            val updateState = backStackEntry.arguments?.getString("updateState")
            val originalIdx = backStackEntry.arguments?.getString("originalIdx")

            if (bitmapType != null) {
                SettingCamera(
                    navController = navController,
                    viewModel = postViewModel,
                    bitmapType = bitmapType,
                    updateState = updateState,
                    originalIdx = originalIdx,
                )
            }
        }

        composable("updateCamera/{bitmapType}/{updateState}/{originalIdx}") { backStackEntry ->
            val bitmapType = backStackEntry.arguments?.getString("bitmapType")
            val updateState = backStackEntry.arguments?.getString("updateState")
            val originalIdx = backStackEntry.arguments?.getString("originalIdx")

            if (bitmapType != null) {
                UpdateSettingCamera(
                    navController = navController,
                    viewModel = updateViewModel,
                    bitmapType = bitmapType,
                    updateState = updateState,
                    originalIdx = originalIdx,
                )
            }
        }

        composable("pattingCamera") {
            SettingPattingCamera(navController = navController, viewModel = proofViewModel)
        }

        composable(
            route = "patUpdate/{patId}",
            arguments = listOf(
                navArgument("patId") {
                    type = NavType.LongType
                    defaultValue = -1
                }
            )) { backStackEntry ->
            val patId = backStackEntry.arguments?.getLong("patId") ?: -1
            PatUpdateView(
                patId = patId,
                patUpdateViewModel = updateViewModel,
                navController = navController)
        }
        composable(
            route = "participatingDetail/{patId}",
            arguments = listOf(
                navArgument("patId") {
                    type = NavType.LongType
                    defaultValue = -1
                }
            )) {
            ProofScreenView(navController = navController)
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