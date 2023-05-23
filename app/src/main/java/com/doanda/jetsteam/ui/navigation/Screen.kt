package com.doanda.jetsteam.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
    object Favorite : Screen("favorite")
    object Detail : Screen("home/{countryId}") {
        fun createRoute(countryId: Long) = "home/$countryId"
    }
}