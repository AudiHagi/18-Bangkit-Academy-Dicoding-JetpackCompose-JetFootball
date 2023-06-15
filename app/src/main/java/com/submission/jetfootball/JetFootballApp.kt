package com.submission.jetfootball

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.submission.jetfootball.ui.navigation.NavigationItem
import com.submission.jetfootball.ui.navigation.Screen
import com.submission.jetfootball.ui.screen.about.AboutScreen
import com.submission.jetfootball.ui.screen.detail.DetailScreen
import com.submission.jetfootball.ui.screen.favorite.FavoriteScreen
import com.submission.jetfootball.ui.screen.home.HomeScreen

@Composable
fun JetFootballApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailPlayer.route) {
                BottomBar(navController, currentRoute)
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(snackbarData = data, shape = RoundedCornerShape(8.dp))
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController, scaffoldState)
            }
            composable(
                route = Screen.DetailPlayer.route,
                arguments = listOf(
                    navArgument("playerId") { type = NavType.IntType }
                )
            ) {
                val playerId = it.arguments?.getInt("playerId") ?: 0
                DetailScreen(playerId, navController, scaffoldState, navigateBack = {
                    navController.navigateUp()
                })
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(navController, scaffoldState)
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: String?,
) {
    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = "Favorite",
            icon = Icons.Default.Favorite,
            screen = Screen.Favorite
        ),
        NavigationItem(
            title = "Profile",
            icon = Icons.Default.Person,
            screen = Screen.About
        ),
    )

    BottomNavigation(backgroundColor = Color.White) {
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
                selectedContentColor = MaterialTheme.colors.primaryVariant,
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