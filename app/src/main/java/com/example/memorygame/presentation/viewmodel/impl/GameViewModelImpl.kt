package com.example.memorygame.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    override val images = MutableSharedFlow<List<CardData>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    private val _message = MutableLiveData<Boolean>()
    override var message: LiveData<Boolean> = _message

    private val _open = MutableLiveData<Int>()
    override val open: LiveData<Int> = _open

    private val _openAction = MutableLiveData<Int>()
    override val openAction: LiveData<Int> = _openAction

    private val _close = MutableLiveData<Int>()
    override val close: LiveData<Int> = _close

    private val _closeWithAction = MutableLiveData<Int>()
    override val closeWithAction: LiveData<Int> = _closeWithAction

    private val _hide = MutableLiveData<Int>()
    override val hide: LiveData<Int> = _hide

    private val _hideWithAnim = MutableLiveData<Int>()
    override val hideWithAnim: LiveData<Int> = _hideWithAnim

    override val canClick = MutableLiveData(true)

    override fun loadImages(level: LevelEnum) {
        repository.getImagesByLevel(level)
            .onEach { imgs ->
                images.emit(imgs)
            }
            .launchIn(viewModelScope)
    }

    private var firstIndex = -1
    private var secondIndex = -1
    private val clicks = ArrayList<CardData>()

    override fun checkMatchingCards(clickView: CardData, clickedIndex: Int) {
        viewModelScope.launch {

//            "clicked $clickedIndex".myLog()

            if (firstIndex != -1 && secondIndex != -1) return@launch
            if (firstIndex == clickedIndex || secondIndex == clickedIndex) return@launch

            if (firstIndex == -1) {
                clicks.add(clickView)
                firstIndex = clickedIndex
                _open.value = clickedIndex
            }
            else {
                clicks.add(clickView)
                secondIndex = clickedIndex
                _openAction.value = clickedIndex

                delay(1000)

                if (clicks[0].id == clicks[1].id) {
                    _hide.value = firstIndex
                    _hideWithAnim.value = secondIndex
                }
                else {
                    _close.value = firstIndex
                    _closeWithAction.value = secondIndex
                }

                firstIndex = -1
                secondIndex = -1

                clicks.clear()
            }
        }
    }

    override fun restartGame() {
        clicks.clear()
        firstIndex = -1
        secondIndex = -1
    }
}