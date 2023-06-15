package com.submission.jetfootball.ui.screen.favorite

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.submission.jetfootball.data.local.Player
import com.submission.jetfootball.ui.common.UiState
import com.submission.jetfootball.ui.components.AvailableContent
import com.submission.jetfootball.ui.components.EmptyContent

@Composable
fun FavoriteScreen(navController: NavController, scaffoldState: ScaffoldState) {
    val favoriteViewModel = hiltViewModel<FavoriteViewModel>()

    favoriteViewModel.allFavoritePlayer.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {}
            is UiState.Error -> {}
            is UiState.Success -> {
                FavoriteContent(
                    listFavoritePlayer = uiState.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    onUpdateFavoriteTourism = favoriteViewModel::updateFavoritePlayer
                )
            }
        }
    }
}

@Composable
fun FavoriteContent(
    listFavoritePlayer: List<Player>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteTourism: (id: Int, isFavorite: Boolean) -> Unit
) {
    when (listFavoritePlayer.isEmpty()) {
        true -> EmptyContent()
        false -> AvailableContent(
            listFavoritePlayer,
            navController,
            scaffoldState,
            onUpdateFavoriteTourism
        )
    }
}