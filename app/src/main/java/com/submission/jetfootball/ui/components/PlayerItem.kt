package com.submission.jetfootball.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.submission.jetfootball.data.local.Player
import com.submission.jetfootball.ui.theme.Shapes
import kotlinx.coroutines.launch

@Composable
fun AvailableContent(
    listPlayer: List<Player>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoritePlayer: (id: Int, isFavorite: Boolean) -> Unit,
) {
    LazyColumn {
        items(listPlayer, key = { it }) { tourism ->
            PlayerItem(tourism, navController, scaffoldState, onUpdateFavoritePlayer)
        }
    }
}

@Composable
fun PlayerItem(
    player: Player,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoritePlayer: (id: Int, isFavorite: Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val (id, name, image, _, _, _, isFavorite) = player
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .border(1.dp, Color.Gray) // add border
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(Shapes.medium)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
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
    }
}