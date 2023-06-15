package com.submission.jetfootball.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val photoUrl: String,
    val cost: String,
    val club: String,
    val desc: String,
    var isFavorite: Boolean = false,
)