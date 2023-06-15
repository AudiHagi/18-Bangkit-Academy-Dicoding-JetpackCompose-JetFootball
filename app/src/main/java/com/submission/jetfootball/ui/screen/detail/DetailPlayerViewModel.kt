package com.submission.jetfootball.ui.screen.detail

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
class DetailPlayerViewModel @Inject constructor(private val repository: PlayerRepository) : ViewModel() {
    private val _player = MutableStateFlow<UiState<Player>>(UiState.Loading)
    val player = _player.asStateFlow()

    fun getPlayer(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPlayer(id)
                .catch { _player.value = UiState.Error(it.message.toString()) }
                .collect { _player.value = UiState.Success(it) }
        }
    }

    fun updateFavoritePlayer(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoritePlayer(id, isFavorite)
        }
    }
}