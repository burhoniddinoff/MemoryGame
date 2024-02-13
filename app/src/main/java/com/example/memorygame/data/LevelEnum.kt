package com.example.memorygame.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class LevelEnum(val horCount: Int, val verCount: Int) : Parcelable {
    EASY(3, 4), MEDIUM(4, 6), HARD(6, 8)
}

