package com.submission.jetfootball.ui.screen.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.submission.jetfootball.data.local.Player
import com.submission.jetfootball.ui.common.UiState
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    playerId: Int,
    navController: NavController,
    scaffoldState: ScaffoldState,
    navigateBack: () -> Unit
) {
    val detailViewModel = hiltViewModel<DetailPlayerViewModel>()
    detailViewModel.player.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> detailViewModel.getPlayer(playerId)
            is UiState.Error -> {}
            is UiState.Success -> {
                DetailContent(
                    uiState.data,
                    navController,
                    scaffoldState,
                    onBackClick = navigateBack,
                    detailViewModel::updateFavoritePlayer
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    player: Player,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onBackClick: () -> Unit,
    onUpdateFavoritePlayer: (id: Int, isFavorite: Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val (id, name, photo, cost, club, desc, isFavorite) = player
    Column(modifier = Modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = photo,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(16.dp, 60.dp, 16.dp, 0.dp)
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 20.dp,
                                bottomEnd = 20.dp,
                                topStart = 20.dp,
                                topEnd = 20.dp
                            )
                        )
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(
                                bottomStart = 20.dp,
                                bottomEnd = 20.dp,
                                topStart = 20.dp,
                                topEnd = 20.dp
                            )
                        )
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(com.submission.jetfootball.R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Icon(
                    imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    tint = if (isFavorite) Color.Red else MaterialTheme.colors.onSurface,
                    contentDescription = name,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(100))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onUpdateFavoritePlayer(id ?: 0, !isFavorite)
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "$name ${if (isFavorite) "removed from" else "added as"} favorite ",
                                    actionLabel = "Dismiss",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        },
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = club,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Justify,
                    )
                    Text(
                        text = " - ",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Justify,
                    )
                    Text(
                        text = cost,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Justify,
                    )
                }
                Text(
                    text = desc,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                )
            }
        }
    }
}