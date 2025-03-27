package com.example.destination.ui.utils

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


@SuppressLint("ClickableViewAccessibility")
fun View.applyPressEffect(movableContent: LinearLayout, imageView: ImageView, textView: TextView) {
    var hasExited = false // Track if user exits the button area

    this.setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                hasExited = false // Reset when user touches again
                animateDown(imageView, textView)
                animateButtonPress(movableContent, pressed = true)
            }

            MotionEvent.ACTION_MOVE -> {
                if (!isTouchInsideView(v, event) && !hasExited) {
                    animateUp(imageView, textView) // Reset animation when exiting once
                    hasExited = true
                    animateButtonPress(movableContent, pressed = false)
                }
            }

            MotionEvent.ACTION_UP -> {
                hasExited = true
                animateUp(imageView, textView)
                animateButtonPress(movableContent, pressed = false)
            }
        }
        false
    }
}

// Check if touch is inside the view's area
private fun isTouchInsideView(view: View, event: MotionEvent): Boolean {
    val x = event.x
    val y = event.y
    return x >= 0 && x <= view.width && y >= 0 && y <= view.height
}

// Move element down
private fun animateUp(vararg views: View) {
    for (view in views) {
        ObjectAnimator.ofFloat(view, "translationY", 6f, 0f).apply {
            duration = 0
            interpolator = OvershootInterpolator()
            start()
        }
    }
}

// Move element up
private fun animateDown(vararg views: View) {
    for (view in views) {
        ObjectAnimator.ofFloat(view, "translationY", 0f, 6f).apply {
            duration = 0
            interpolator = OvershootInterpolator()
            start()
        }
    }
}

// Function to animate only the text and image, not the background
private fun animateButtonPress(view: View, pressed: Boolean) {
    val translationY = if (pressed) 6f else 0f

    ObjectAnimator.ofFloat(view, "translationY", translationY).apply {
        duration = 0
        interpolator = OvershootInterpolator()
        start()
    }
}


