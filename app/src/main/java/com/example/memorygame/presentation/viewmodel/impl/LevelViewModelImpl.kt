package com.example.memorygame.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import com.example.memorygame.domain.AppRepository
import com.example.memorygame.presentation.viewmodel.LevelViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LevelViewModelImpl @Inject constructor(private val repository: AppRepository) : ViewModel(), LevelViewModel {
//    override val openGameFlow = flow<LevelEnum> {  }
//
//    override fun openGameByLevel(level: LevelEnum) {
//        openGameFlow.
//    }
}


