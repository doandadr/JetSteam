package com.doanda.jetsteam

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.doanda.jetsteam.ui.components.BottomBar
import com.doanda.jetsteam.ui.navigation.Screen
import com.doanda.jetsteam.ui.screen.about.AboutScreen
import com.doanda.jetsteam.ui.screen.detail.DetailScreen
import com.doanda.jetsteam.ui.screen.favorite.FavoriteScreen
import com.doanda.jetsteam.ui.screen.home.HomeScreen
import com.doanda.jetsteam.ui.theme.JetSteamTheme

// TODO
@Composable
fun JetSteamApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { gameId ->
                        navController.navigate(Screen.Detail.createRoute(gameId))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen { gameId ->
                    navController.navigate(Screen.Detail.createRoute(gameId))
                }
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("gameId") { type = NavType.LongType}),
            ) {
                val id = it.arguments?.getLong("gameId") ?: -1L
                DetailScreen(
                    gameId = id,
                    navigateToBack = {
                        navController.navigateUp()
                    }
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun JetAseanAppPreview() {
    JetSteamTheme() {
        JetSteamApp()
    }
}
