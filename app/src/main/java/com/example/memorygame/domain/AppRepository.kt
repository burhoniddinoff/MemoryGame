package com.example.memorygame.domain

import com.example.memorygame.data.CardData
import com.example.memorygame.data.LevelEnum
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun getAllCardsByLevel(level : LevelEnum) : Flow<List<CardData>>

}