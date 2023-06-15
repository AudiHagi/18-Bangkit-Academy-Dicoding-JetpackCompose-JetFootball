package com.submission.jetfootball.di

import android.app.Application
import androidx.room.Room
import com.submission.jetfootball.data.local.PlayerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application) = Room
        .databaseBuilder(application, PlayerDatabase::class.java, "football.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideDao(database: PlayerDatabase) = database.playerDao()
}