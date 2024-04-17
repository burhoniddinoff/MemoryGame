package com.example.memorygame.utils

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import com.example.memorygame.R
import com.example.memorygame.utils.MyPref

class MyMusicPlayer private constructor() {
    private val openCardMusic by lazy { MediaPlayer.create(context, R.raw.open) }
    private val settings: MyPref by lazy { MyPref.getInstanceForMusic(context) }
    private val closeCardMusic by lazy { MediaPlayer.create(context, R.raw.close) }
    private val removeCard by lazy { MediaPlayer.create(context, R.raw.removecard) }
    private val mainMusic by lazy { MediaPlayer.create(context, R.raw.long_music) }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        @SuppressLint("StaticFieldLeak")
        private lateinit var instance: MyMusicPlayer

        fun getInstance(): MyMusicPlayer {
            if (!(Companion::instance.isInitialized)) {
                instance = MyMusicPlayer()
            }
            return instance
        }

        fun init(context: Context) {
            Companion.context = context
        }
    }

    fun startCloseCardMusic() {
        if (settings.getMusicState())

            closeCardMusic.start()
    }



    fun startOpenCardMusic() {
        if (settings.getMusicState())
            openCardMusic.start()
    }



    fun startRemoveCardMusic() {
        if (settings.getMusicState())
            removeCard.start()
    }


    /* fun startMainMusic() {
         if (settings.getMusicState()) {

             mainMusic.start()
             mainMusic.isLooping = true
         }
     }

     fun stopMainMusic() {
         mainMusic.pause()
     }*/


    fun manageMusic(): Boolean {

        if (settings.getMusicState()) {
            settings.onOrOffMusic(false)
            return false
        } else
            settings.onOrOffMusic(true)
        return true
    }

}