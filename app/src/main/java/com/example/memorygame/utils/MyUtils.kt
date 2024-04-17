package com.example.memorygame.utils

import android.animation.ObjectAnimator
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.memorygame.R
import com.example.memorygame.data.CardData

//
//fun ImageView.openFirstAnim() {
//    this.animate()
//        .setDuration(1000)
//        .rotationY(89f)
//        .scaleY(0.5f)
//        .withEndAction {
//            this.rotationY = -89f
//            val cardData = this.tag as CardData
//            this.setImageResource(cardData.resId)
//            this.animate()
//                .setDuration(1000)
//                .scaleY(1f)
//                .rotationY(0f)
//                .start()
//        }
//        .start()
//}
//
//fun ImageView.openSecondAnim(endAnim: () -> Unit) {
//    this.animate()
//        .setDuration(1000)
//        .rotationY(89f)
//        .withEndAction {
//            this.rotationY = -89f
//            val cardData = this.tag as CardData
//            this.setImageResource(cardData.resId)
//            this.animate()
//                .setDuration(1000)
//                .rotationY(0f)
//                .withEndAction(endAnim)
//                .start()
//        }
//        .start()
//}
//
//fun ImageView.closeAnim() {
//    this.animate()
//        .setDuration(1000)
//        .rotationY(-89f)
//        .withEndAction {
//            this.rotationY = 89f
//            this.setImageResource(R.drawable.image_back)
//            this.animate()
//                .setDuration(1000)
//                .rotationY(0f)
//                .start()
//        }
//        .start()
//}
//
//fun ImageView.closeAnim(endAnim: () -> Unit) {
//    this.animate()
//        .setDuration(1000)
//        .rotationY(-89f)
//        .withEndAction {
//            this.rotationY = 89f
//            this.setImageResource(R.drawable.image_back)
//            this.animate()
//                .setDuration(1000)
//                .rotationY(0f)
//                .withEndAction(endAnim)
//                .start()
//        }
//        .start()
//}
//
//fun ImageView.hideAnim() {
//    this.animate()
//        .setDuration(1000)
//        .scaleX(0f)
//        .scaleY(0f)
//        .start()
//}
//
//fun ImageView.hideAnim(endAnim: () -> Unit) {
//    this.animate()
//        .setDuration(1000)
//        .scaleX(0f)
//        .scaleY(0f)
//        .withEndAction(endAnim)
//        .start()
//}
//
//


//

//fun String.myLog(tag: String = "TTT") = Timber.tag(tag).d(this)

fun ImageView.animateBounce() {
    val bounceAnimator = ObjectAnimator.ofFloat(this, "translationY", 0f, 0f, 0f)
    bounceAnimator.interpolator = BounceInterpolator()
    bounceAnimator.duration = 1000 // Set the duration of the animation
    bounceAnimator.start()
}


fun ImageView.openImage() {
    animate()
        .setDuration(350)
        .rotationY(89f)
        .withEndAction {
            setImageResource((this.tag as CardData).resID)
            rotationY = -89f

            animate()
                .setDuration(350)
                .rotationY(0f)
                .withEndAction {
                }
                .start()
        }
        .start()
}

fun ImageView.openImage(endAnim: () -> Unit) {
    animate()
        .setDuration(350)
        .rotationY(89f)
        .withEndAction {
            setImageResource((this.tag as CardData).resID)
            rotationY = -89f

            animate()
                .setDuration(350)
                .rotationY(0f)
                .withEndAction {
                    endAnim.invoke()
                }
                .start()
        }
        .start()
}

fun ImageView.closeImage() {
    this.apply {
        animate()
            .setDuration(350)
            .rotationY(89f)
            .withEndAction {
                setImageResource(R.drawable.bg1)
                rotationY = -89f

                animate()
                    .setDuration(350)
                    .rotationY(0f)
                    .withEndAction {
                    }
                    .start()
            }
            .start()
    }

}

fun ImageView.closeImage(endAnim: () -> Unit) {
    animate()
        .setDuration(350)
        .rotationY(89f)
        .withEndAction {
            setImageResource(R.drawable.bg1)
            rotationY = -89f

            animate()
                .setDuration(350)
                .rotationY(0f)
                .withEndAction {
                    endAnim.invoke()
                }
                .start()
        }
        .start()
}

fun ImageView.hideAnim() {
    this.animate()
        .setDuration(500)
        .alpha(0.5f)
//        .scaleX(0f)
//        .scaleY(0f)
        .start()
}

fun ImageView.hideAnim(endAnim: () -> Unit) {
    this.animate()
        .setDuration(500)
        .alpha(0.5f)
//        .scaleX(0f)
//        .scaleY(0f)
        .withEndAction(endAnim)
        .start()
}


fun ConstraintLayout.clickLevelButton(click: () -> Unit) {
    this.animate()
        .scaleX(0.7f)
        .setDuration(350)
        .scaleY(0.7f)
        .withEndAction {
            this.animate()
                .setDuration(90)
                .scaleY(1f)
                .scaleX(1f)
                .withEndAction {
                    click.invoke()
                }
                .start()
        }
        .start()
}
