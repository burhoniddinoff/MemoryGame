package com.example.memorygame.presentation.screens

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.memorygame.R
import com.example.memorygame.data.LevelEnum
import com.example.memorygame.databinding.ScreenLevelBinding
import com.example.memorygame.utils.clickLevelButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelScreen : Fragment(R.layout.screen_level) {

    private val binding: ScreenLevelBinding by viewBinding(ScreenLevelBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        val imgTxtMemory = binding.imgTxtMemory

        val linearGradientMemory = LinearGradient(
            0f, 0f, 0f, imgTxtMemory.textSize,
            intArrayOf(0xFFFFFFFF.toInt(), 0xFFA2F6FF.toInt(), 0xFF04D1FE.toInt()),
            null,
            Shader.TileMode.CLAMP
        )

        imgTxtMemory.paint.shader = linearGradientMemory


        val imgTxtGame = binding.imgTxtGame

        val linearGradientGame = LinearGradient(
            0f, 0f, 0f, imgTxtGame.textSize,
            intArrayOf(0xFFF5EFBB.toInt(), 0xFFFFE728.toInt(), 0xFFFFA800.toInt()),
            null,
            Shader.TileMode.CLAMP
        )

        imgTxtGame.paint.shader = linearGradientGame


        var clickItem: ConstraintLayout? = null

        binding.containerEasy.setOnClickListener {
//            "click easy".myLog()

            if (clickItem == null) {
                clickItem = binding.containerEasy
                binding.containerEasy.clickLevelButton {
                    findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(LevelEnum.EASY))
                }
            }

        }

        binding.containerMedium.setOnClickListener {
//            "click medium".myLog()

            if (clickItem == null) {
                clickItem = binding.containerMedium
                binding.containerMedium.clickLevelButton {
                    findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(LevelEnum.MEDIUM))
                }
            }
        }


        binding.containerHard.setOnClickListener {
//            "click hard".myLog()

            if (clickItem == null) {
                clickItem = binding.containerHard
                binding.containerHard.clickLevelButton {
                    findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(LevelEnum.HARD))
                }
            }
        }

    }
}