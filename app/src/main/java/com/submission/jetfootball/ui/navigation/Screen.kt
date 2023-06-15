package com.submission.jetfootball.ui.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object DetailPlayer : Screen("home/{playerId}") {
        fun createRoute(playerId: Long) = "home/$playerId"
    }

}