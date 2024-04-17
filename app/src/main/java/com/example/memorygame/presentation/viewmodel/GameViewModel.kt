package com.example.memorygame.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.example.memorygame.data.CardData
import com.example.memorygame.data.LevelEnum
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface GameViewModel {
    val images: MutableSharedFlow<List<CardData>>
    var message: LiveData<Boolean>
    val close: LiveData<Int>
    val canClick: LiveData<Boolean>
    val hide: LiveData<Int>

    fun checkMatchingCards(clickView: CardData, clickedIndex: Int)
    fun loadImages(level: LevelEnum)

    fun restartGame()
    val open: LiveData<Int>
    val closeWithAction: LiveData<Int>
    val openAction: LiveData<Int>
    val hideWithAnim: LiveData<Int>
}

