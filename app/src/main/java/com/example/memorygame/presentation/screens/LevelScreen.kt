package com.example.memorygame.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.memorygame.R
import com.example.memorygame.data.LevelEnum
import com.example.memorygame.databinding.ScreenLevelBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelScreen : Fragment(R.layout.screen_level) {

    private val binding by viewBinding(ScreenLevelBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.easy.setOnClickListener { navController.navigate(LevelScreenDirections.actionLevelScreenToGameScreen(LevelEnum.EASY)) }
        binding.medium.setOnClickListener { navController.navigate(LevelScreenDirections.actionLevelScreenToGameScreen(LevelEnum.MEDIUM)) }
        binding.hard.setOnClickListener {navController.navigate(LevelScreenDirections.actionLevelScreenToGameScreen(LevelEnum.HARD)) }


    }

}