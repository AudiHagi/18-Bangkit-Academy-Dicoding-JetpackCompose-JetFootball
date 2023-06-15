package com.submission.jetfootball.data

import com.submission.jetfootball.data.local.Player
import com.submission.jetfootball.data.local.PlayerDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(private val playerDao: PlayerDao) {
    fun getAllPlayer() = playerDao.getAllPlayer()
    fun getAllFavoritePlayer() = playerDao.getAllFavoritePlayer()
    fun getPlayer(id: Int) = playerDao.getPlayer(id)
    fun searchPlayer(query: String) = playerDao.searchPlayer(query)
    suspend fun insertAllPlayer(player: List<Player>) = playerDao.insertAllPlayer(player)
    suspend fun updateFavoritePlayer(id: Int, isFavorite: Boolean) =
        playerDao.updateFavoritePlayer(id, isFavorite)
}