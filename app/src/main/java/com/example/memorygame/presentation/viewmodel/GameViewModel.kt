package com.example.memorygame.presentation.viewmodel

import com.example.memorygame.data.CardData
import com.example.memorygame.data.LevelEnum
import kotlinx.coroutines.flow.SharedFlow

interface GameViewModel {
    val cardFlow: SharedFlow<List<CardData>>
    val closeAllViewsFlow: SharedFlow<Unit>

    fun loadCardByLevel(level: LevelEnum)
}

