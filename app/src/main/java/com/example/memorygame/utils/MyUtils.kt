package com.example.memorygame.utils

import android.widget.ImageView
import com.example.memorygame.R
import com.example.memorygame.data.CardData


fun ImageView.openFirstAnim() {
    this.animate()
        .setDuration(1000)
        .rotationY(89f)
        .scaleY(0.5f)
        .withEndAction {
            this.rotationY = -89f
            val cardData = this.tag as CardData
            this.setImageResource(cardData.resId)
            this.animate()
                .setDuration(1000)
                .scaleY(1f)
                .rotationY(0f)
                .start()
        }
        .start()
}

fun ImageView.openSecondAnim(endAnim: () -> Unit) {
    this.animate()
        .setDuration(1000)
        .rotationY(89f)
        .withEndAction {
            this.rotationY = -89f
            val cardData = this.tag as CardData
            this.setImageResource(cardData.resId)
            this.animate()
                .setDuration(1000)
                .rotationY(0f)
                .withEndAction(endAnim)
                .start()
        }
        .start()
}

fun ImageView.closeAnim() {
    this.animate()
        .setDuration(1000)
        .rotationY(-89f)
        .withEndAction {
            this.rotationY = 89f
            this.setImageResource(R.drawable.image_back)
            this.animate()
                .setDuration(1000)
                .rotationY(0f)
                .start()
        }
        .start()
}

fun ImageView.closeAnim(endAnim: () -> Unit) {
    this.animate()
        .setDuration(1000)
        .rotationY(-89f)
        .withEndAction {
            this.rotationY = 89f
            this.setImageResource(R.drawable.image_back)
            this.animate()
                .setDuration(1000)
                .rotationY(0f)
                .withEndAction(endAnim)
                .start()
        }
        .start()
}

fun ImageView.hideAnim() {
    this.animate()
        .setDuration(1000)
        .scaleX(0f)
        .scaleY(0f)
        .start()
}

fun ImageView.hideAnim(endAnim: () -> Unit) {
    this.animate()
        .setDuration(1000)
        .scaleX(0f)
        .scaleY(0f)
        .withEndAction(endAnim)
        .start()
}

