package com.submission.jetfootball.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.submission.jetfootball.data.local.Player
import com.submission.jetfootball.ui.common.UiState
import com.submission.jetfootball.ui.components.AvailableContent
import com.submission.jetfootball.ui.components.EmptyContent
import com.submission.jetfootball.ui.components.SearchBar

@Composable
fun HomeScreen(navController: NavController, scaffoldState: ScaffoldState) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val homeState by homeViewModel.homeState

    homeViewModel.allPlayer.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {}
            is UiState.Error -> {}
            is UiState.Success -> {
                HomeContent(
                    listPlayer = uiState.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    query = homeState.query,
                    onQueryChange = homeViewModel::onQueryChange,
                    onUpdateFavoriteTourism = homeViewModel::updateFavoritePlayer
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    listPlayer: List<Player>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    query: String,
    onQueryChange: (String) -> Unit,
    onUpdateFavoriteTourism: (id: Int, isFavorite: Boolean) -> Unit
) {
    Column {
        SearchBar(query = query, onQueryChange = onQueryChange)
        when (listPlayer.isEmpty()) {
            true -> EmptyContent()
            false -> AvailableContent(
                listPlayer,
                navController,
                scaffoldState,
                onUpdateFavoriteTourism
            )
        }
    }
}