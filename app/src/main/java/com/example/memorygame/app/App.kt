package com.example.memorygame.app

import android.app.Application
import com.example.memorygame.utils.MyMusicPlayer
import com.example.memorygame.utils.MyPref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        MyPref.init(this)
        MyMusicPlayer.init(this)
//        Timber.plant(Timber.DebugTree())
    }
}