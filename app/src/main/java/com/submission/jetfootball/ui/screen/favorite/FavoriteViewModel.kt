package com.submission.jetfootball.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submission.jetfootball.data.PlayerRepository
import com.submission.jetfootball.data.local.Player
import com.submission.jetfootball.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: PlayerRepository) :
    ViewModel() {
    private val _allFavoritePlayer = MutableStateFlow<UiState<List<Player>>>(UiState.Loading)
    val allFavoritePlayer = _allFavoritePlayer.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavoritePlayer()
                .catch { _allFavoritePlayer.value = UiState.Error(it.message.toString()) }
                .collect { _allFavoritePlayer.value = UiState.Success(it) }
        }
    }

    fun updateFavoritePlayer(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoritePlayer(id, isFavorite)
        }
    }
}