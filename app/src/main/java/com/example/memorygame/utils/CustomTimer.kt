package com.example.memorygame.utils

import android.os.CountDownTimer

class CustomTimer(private val totalTime: Long, private val interval: Long) {
    private var remainingTime: Long = totalTime
    private var timer: CountDownTimer? = null
    private var isPaused = false
    private var isCanceled = false
    private var onFinishListener: (() -> Unit)? = null
    private var onTickListener: ((Long) -> Unit)? = null

    fun start() {
        timer = object : CountDownTimer(remainingTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
//                "Har sekund $remainingTime".myLog()
                remainingTime = millisUntilFinished
                onTickListener?.invoke(millisUntilFinished)
            }

            override fun onFinish() {
                onFinishListener?.invoke()
            }
        }.start()
    }

    fun pause() {
        timer?.cancel()
        isPaused = true
    }

    fun resume() {
        if (isPaused && !isCanceled) {
            start()
            isPaused = false
        }
    }

    fun stop() {
        timer?.cancel()
        isCanceled = true
    }

    fun setOnFinishListener(listener: () -> Unit) {
        onFinishListener = listener
    }

    fun setOnTickListener(listener: (Long) -> Unit) {
        onTickListener = listener
    }
}
