package io.github.ketcktsd.anktfw.animation.template

import android.view.View
import android.view.animation.AlphaAnimation
import io.github.ketcktsd.anktfw.animation.core.anim.alphaAnim


private typealias AlphaInit = AlphaAnimation.() -> Unit

inline fun View.fadeIn(init: AlphaInit = {
    duration = 300L
    fillAfter = true
}) = startAnimation(alphaAnim(0F, 1F, init))

inline fun View.fadeOut(init: AlphaInit = {
    duration = 300L
    fillAfter = true
}) = startAnimation(alphaAnim(1F, 0F, init))
