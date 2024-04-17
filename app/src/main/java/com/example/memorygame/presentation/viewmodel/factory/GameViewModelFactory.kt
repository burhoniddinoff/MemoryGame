package com.example.memorygame.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.memorygame.domain.AppRepositoryImpl
import com.example.memorygame.presentation.viewmodel.impl.GameViewModelImpl


class GameViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModelImpl::class.java))
            return GameViewModelImpl(AppRepositoryImpl.getInstance()) as T

        throw IllegalArgumentException()
    }
}




