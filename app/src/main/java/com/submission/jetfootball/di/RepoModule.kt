package com.submission.jetfootball.di

import com.submission.jetfootball.data.PlayerRepository
import com.submission.jetfootball.data.local.PlayerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {
    @Provides
    @ViewModelScoped
    fun provideRepository(playerDao: PlayerDao) = PlayerRepository(playerDao)
}