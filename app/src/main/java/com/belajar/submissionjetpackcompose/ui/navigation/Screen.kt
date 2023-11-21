package com.belajar.submissionjetpackcompose.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object DetailScreen: Screen("home/{foodId}") {
        fun createRoute(foodId: String) = "home/$foodId"
    }
}
