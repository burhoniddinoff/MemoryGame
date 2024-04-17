package com.example.memorygame.presentation.screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.memorygame.R
import com.example.memorygame.data.CardData
import com.example.memorygame.data.LevelEnum
import com.example.memorygame.databinding.ScreenGameBinding
import com.example.memorygame.presentation.viewmodel.impl.GameViewModelImpl
import com.example.memorygame.utils.CustomTimer
import com.example.memorygame.utils.MyMusicPlayer
import com.example.memorygame.utils.MyPref
import com.example.memorygame.utils.closeImage
import com.example.memorygame.utils.hideAnim
import com.example.memorygame.utils.openImage
import com.example.memorygame.presentation.viewmodel.factory.GameViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameScreen : Fragment(R.layout.screen_game) {
    private val viewModel: GameViewModelImpl by viewModels { GameViewModelFactory() }
    private val binding: ScreenGameBinding by viewBinding(ScreenGameBinding::bind)
    private var x = 0
    private var y = 0
    private val views = ArrayList<ImageView>()
    private var cardWidth = 0f
    private var cardHeight = 0f
    private var centerX = 0f
    private var centerY = 0f
    private val navArgs by navArgs<GameScreenArgs>()
    private var findCards = 0
    private var canClick = true
    private lateinit var level: LevelEnum
    private var timeTotal: Int = 5
    private var levelInt = 1
    private var attempts = 0
    private lateinit var player: MyMusicPlayer
    private lateinit var timer: CustomTimer
    private var timeI = 10

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setupOnBackPressed()

        level = navArgs.level

        timeTotal =
            when (level) {
                LevelEnum.EASY -> 100
                LevelEnum.MEDIUM -> 200
                else -> 300
            }

        timeI = timeTotal

        playerSet()

        // ekran chizildi
        binding.container.post {
            // x y pozitsiya topildi
            x = level.rowCount
            y = level.columnCount

            cardWidth = binding.container.width.toFloat() / x
            cardHeight = binding.container.height.toFloat() / y

            centerX = binding.container.width / 2f
            centerY = binding.container.height / 2f


            viewModel.loadImages(level) // viewmodelga rasmlar keldi
        }

        viewModel.images.onEach {
            loadViews(it)
            delay(5000)
            clickEvent()
            timerInitAndStart()
        }
            .launchIn(lifecycleScope)

        viewModel.open.observe(viewLifecycleOwner, openObserver)
        viewModel.openAction.observe(viewLifecycleOwner, openWithActionObserver)

        viewModel.close.observe(viewLifecycleOwner, closeObserver)
        viewModel.closeWithAction.observe(viewLifecycleOwner, closeWithActionObserver)

        viewModel.hide.observe(viewLifecycleOwner, hideObserver)
        viewModel.hideWithAnim.observe(viewLifecycleOwner, hideWithAnimObserver)

        binding.reload.setOnClickListener {
//            binding.container.removeAllViews()
//            viewModel.restartGame()
//            viewModel.loadImages(level)
//            timerInitAndStart()
        }

        binding.menu.setOnClickListener {
            backDialog()
        }

        binding.level.text = "${MyPref.getInstance().getLevel(level)}"
        levelInt = MyPref.getInstance().getLevel(level)
    }


    private fun playerSet() {
        player = MyMusicPlayer.getInstance()
        val pref = MyPref.getInstance()

        if (pref.getMusicState()) {
            binding.volume.setImageResource(R.drawable.sound_on)
        } else {
            binding.volume.setImageResource(R.drawable.sound_off)
        }
        binding.containerVolume.setOnClickListener {
            if (pref.getMusicState()) {
                player.manageMusic()
                binding.volume.setImageResource(R.drawable.sound_off)
            } else {
                player.manageMusic()
                binding.volume.setImageResource(R.drawable.sound_on)
            }
        }
    }

    private fun loadViews(images: List<CardData>) {
        val margin = 20 // Margin value

        val availableCardWidth = (cardWidth - margin)
        val availableCardHeight = (cardHeight - margin)

        for (i in 0 until x) {
            for (j in 0 until y) {

                val image = ImageView(requireContext())

                val lp = ConstraintLayout.LayoutParams(
                    availableCardWidth.toInt(),
                    availableCardWidth.toInt()
                )

                var paddingVertical = 30
                var paddingHorizontal = 20

                when (level) {
                    LevelEnum.EASY -> {
                        paddingHorizontal = 50
                        paddingVertical = 40
                    }

                    LevelEnum.MEDIUM -> {
                        paddingHorizontal = 40
                        paddingVertical = 30
                    }

                    else -> {}
                }

                image.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)

                image.layoutParams = lp

                image.setBackgroundResource(R.drawable.bg_card)

                image.x = centerX - cardWidth / 2
                image.y = centerY - cardHeight / 2

                image.isEnabled = false

                image.animate()
                    .setDuration(1000)
                    .x((i * cardWidth) + (margin / 2))
                    .y((j * cardHeight) + (margin / 2))
                    .withEndAction {
                        image.openImage {
                            lifecycleScope.launch {
                                delay(1500L)
                                image.closeImage()
                                image.isEnabled = true
                            }
                        }
                    }

                image.setImageResource(R.drawable.quiz)

                binding.container.addView(image)

                image.tag = images[i * level.columnCount + j]

                views.add(image)
            }
        }
    }


    private fun clickEvent() {
        views.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                if (!canClick) return@setOnClickListener
                viewModel.checkMatchingCards(imageView.tag as CardData, index)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showGameOverDialog(isWin: Boolean = false) {
        val dialog = Dialog(requireContext())

        binding.container.removeAllViews()

        dialog.setContentView(R.layout.dialog_win)

        dialog.setCancelable(false)

        dialog.findViewById<TextView>(R.id.level).text = "$levelInt"
        dialog.findViewById<TextView>(R.id.attempts).text = "$attempts"

        dialog.findViewById<LinearLayout>(R.id.home).setOnClickListener {
            dialog.dismiss()
            findNavController().navigateUp()
        }


        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation

        if (isWin) {
            dialog.findViewById<TextView>(R.id.tv_next).text = "Next"
            binding.level.text = "${++levelInt}"
        } else dialog.findViewById<TextView>(R.id.tv_next).text = "Retry"


        dialog.findViewById<LinearLayout>(R.id.next).setOnClickListener {
            dialog.dismiss()

            binding.container.removeAllViews()

            timeTotal =
                when (level) {
                    LevelEnum.EASY -> 100
                    LevelEnum.MEDIUM -> 200
                    else -> 300
                }

            timeI = timeTotal

            lifecycleScope.launch {
                viewModel.restartGame()
                viewModel.loadImages(level)

                delay(5000)

                timerInitAndStart()
            }
        }

        dialog.show()
    }



    private fun timerInitAndStart() {
        if (::timer.isInitialized) {
            timer.stop()
        }

        timer = CustomTimer(totalTime = timeTotal * 1000L, 1000L)

        timer.setOnTickListener { millisUntilFinished ->
            timeI = (millisUntilFinished / 1000).toInt() // Convert milliseconds to seconds
            binding.time.text = timeI.toString()
        }

        timer.setOnFinishListener {
            Toast.makeText(requireContext(), "Game Over", Toast.LENGTH_SHORT).show()
            showGameOverDialog()
        }

        timer.start()
    }


    private val openObserver = Observer<Int> {
        player.startOpenCardMusic()
        views[it].openImage()
    }

    private val openWithActionObserver = Observer<Int> {
        player.startOpenCardMusic()
        views[it].openImage {
            canClick = false
        }
    }


    private val closeObserver = Observer<Int> {
        views[it].closeImage()
    }

    private val closeWithActionObserver = Observer<Int> {
        player.startCloseCardMusic()

        views[it].closeImage {
            ++attempts
            canClick = true
        }
    }

    private val hideObserver = Observer<Int> {
        views[it].hideAnim()

        views[it].apply {
            isClickable = false
            isFocusableInTouchMode = false
            isFocusable = false
        }

    }


    private val hideWithAnimObserver = Observer<Int> { index ->
        player.startRemoveCardMusic()

        views[index].hideAnim {
            canClick = true
            ++attempts
            ++findCards


            isFinish()

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                views[index].apply {
                    isClickable = false
                    isFocusableInTouchMode = false
                    isFocusable = false
                }
            }, 50) // Adjust the delay time as needed
        }
    }


    override fun onStop() {
        MyPref.getInstance().saveLevel(level, levelInt)
        super.onStop()
    }

    private fun backDialog() {
        if (::timer.isInitialized) timer.pause()

        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.dialog_exit)

        dialog.setCancelable(false)



        dialog.findViewById<TextView>(R.id.yes_exit).setOnClickListener {
            dialog.dismiss()
            findNavController().navigateUp()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.attributes?.gravity = Gravity.CENTER

        dialog.findViewById<TextView>(R.id.no_exit).setOnClickListener {
            timer.resume()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun isFinish(): Boolean {
        return if (findCards == (x * y) / 2) {

            timer.stop()

            showGameOverDialog(isWin = true)

            Toast.makeText(requireContext(), "Finish", Toast.LENGTH_SHORT).show()

            true
        } else false
    }


    private fun setupOnBackPressed() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if (::timer.isInitialized) timer.pause()

                val dialog = Dialog(requireContext())

                dialog.setContentView(R.layout.dialog_exit)

                dialog.setCancelable(false)



                dialog.findViewById<TextView>(R.id.yes_exit).setOnClickListener {
                    dialog.dismiss()
                    findNavController().navigateUp()
                }

                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
                dialog.window?.attributes?.gravity = Gravity.CENTER

                dialog.findViewById<TextView>(R.id.no_exit).setOnClickListener {
                    timer.resume()
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


}