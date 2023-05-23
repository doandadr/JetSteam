package com.doanda.jetsteam.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.doanda.jetsteam.ui.navigation.NavigationItem
import com.doanda.jetsteam.ui.navigation.Screen

@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: String?,
) {
    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Filled.Home,
            screen = Screen.Home,
            contentDescription = "home_page",
        ),
        NavigationItem(
            title = "Favorite",
            icon = Icons.Filled.Favorite,
            screen = Screen.Favorite,
            contentDescription = "favorite_page",
        ),
        NavigationItem(
            title = "About",
            icon = Icons.Filled.Person,
            screen = Screen.About,
            contentDescription = "about_page",
        ),
    )

    BottomNavigation(backgroundColor = MaterialTheme.colors.primary) {
        navigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                selectedContentColor = MaterialTheme.colors.secondaryVariant,
                unselectedContentColor = Color.Gray,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }

}