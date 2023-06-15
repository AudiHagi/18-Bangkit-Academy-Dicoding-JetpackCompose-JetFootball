package com.submission.jetfootball.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    fun getAllPlayer(): Flow<List<Player>>

    @Query("SELECT * FROM player WHERE isFavorite = 1")
    fun getAllFavoritePlayer(): Flow<List<Player>>

    @Query("SELECT * FROM player WHERE id = :id")
    fun getPlayer(id: Int): Flow<Player>

    @Query("SELECT * FROM player WHERE name LIKE '%' || :query || '%'")
    fun searchPlayer(query: String): Flow<List<Player>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlayer(tourismList: List<Player>)

    @Query("UPDATE player SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoritePlayer(id: Int, isFavorite: Boolean)
}