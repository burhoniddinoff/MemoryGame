package com.example.memorygame.di

import com.example.memorygame.domain.AppRepository
import com.example.memorygame.domain.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAppRepository(impl: AppRepositoryImpl): AppRepository

}