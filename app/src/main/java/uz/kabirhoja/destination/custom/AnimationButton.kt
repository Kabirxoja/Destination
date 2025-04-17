package uz.kabirhoja.destination.custom

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

object AnimationButton {
    fun View.animateClick(duration: Long = 100, scaleNormal: Float = 1.0f, scaleShrink: Float = 0.9f) {
        val scaleXDown = ObjectAnimator.ofFloat(this, View.SCALE_X, scaleNormal, scaleShrink)
        val scaleYDown = ObjectAnimator.ofFloat(this, View.SCALE_Y, scaleNormal, scaleShrink)

        val scaleXUp = ObjectAnimator.ofFloat(this, View.SCALE_X, scaleShrink, scaleNormal)
        val scaleYUp = ObjectAnimator.ofFloat(this, View.SCALE_Y, scaleShrink, scaleNormal)

        scaleXDown.duration = duration
        scaleYDown.duration = duration
        scaleXUp.duration = duration
        scaleYUp.duration = duration

        val animatorSet = AnimatorSet()
        animatorSet.play(scaleXDown).with(scaleYDown) // Scale down X and Y at the same time
        animatorSet.play(scaleXUp).with(scaleYUp).after(scaleXDown) // Scale up after scale down

        animatorSet.start()
    }
}