package com.example.memorygame.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorygame.data.CardData
import com.example.memorygame.data.LevelEnum
import com.example.memorygame.domain.AppRepository
import com.example.memorygame.presentation.viewmodel.GameViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameViewModelImpl @Inject constructor(private val repository: AppRepository) : ViewModel(), GameViewModel {
    override val cardFlow = MutableSharedFlow<List<CardData>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val closeAllViewsFlow = MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    init {
        viewModelScope.launch {
            delay(1000)
            closeAllViewsFlow.emit(Unit)
        }
    }

    override fun loadCardByLevel(level: LevelEnum) {
        repository.getAllCardsByLevel(level)
            .onEach { cardFlow.emit(it) }
            .launchIn(viewModelScope)
    }
}