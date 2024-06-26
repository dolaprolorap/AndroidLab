package com.example.forandroid.presentation.Animators

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd

class LayoutAnimator {
    fun animate(view: View, width: Int, height: Int, duration: Long = 500) {
        val curHeight = when(view.layoutParams.height) {
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT -> {
                view.measure(View.MeasureSpec.UNSPECIFIED, view.layoutParams.height)
                view.measuredHeight
            }

            else -> view.layoutParams.height
        }
        val curWidth = when(view.layoutParams.width) {
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT -> {
                view.measure(view.layoutParams.width, View.MeasureSpec.UNSPECIFIED)
                view.measuredWidth
            }

            else -> view.layoutParams.width
        }

        view.measure(width, height)
        val finalHeight = when(height) {
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT -> {
                view.measure(View.MeasureSpec.UNSPECIFIED, height)
                view.measuredHeight
            }

            else -> height
        }
        val finalWidth = when(width) {
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT -> {
                view.measure(width, View.MeasureSpec.UNSPECIFIED)
                view.measuredWidth
            }

            else -> width
        }

        val heightAnimator = ValueAnimator
            .ofInt(curHeight, finalHeight)
            .setDuration(duration)

        val widthAnimator = ValueAnimator
            .ofInt(curWidth, finalWidth)
            .setDuration(duration)

        heightAnimator.addUpdateListener { animation ->
            val value = animation.getAnimatedValue()
            view.layoutParams.height = value as Int
            view.requestLayout()
        }
        widthAnimator.addUpdateListener { animation ->
            val value = animation.getAnimatedValue()
            view.layoutParams.width = value as Int
            view.requestLayout()
        }

        heightAnimator.doOnEnd {
            view.layoutParams.height = height
        }
        widthAnimator.doOnEnd {
            view.layoutParams.width = width
        }

        val animationSet = AnimatorSet()
        animationSet.interpolator = AccelerateDecelerateInterpolator()
        animationSet.play(heightAnimator)
        animationSet.play(widthAnimator)
        animationSet.start()
    }
}