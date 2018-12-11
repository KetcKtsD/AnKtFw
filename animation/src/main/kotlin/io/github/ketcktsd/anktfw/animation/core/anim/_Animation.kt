package io.github.ketcktsd.anktfw.animation.core.anim

import android.view.animation.*
import android.view.animation.Animation.AnimationListener

typealias AnimCallback = (anim: Animation) -> Unit

/**
 * set listener to animation
 *
 * @param onStart run on [Animation.AnimationListener.onAnimationStart]
 * @param onRepeat run on [Animation.AnimationListener.onAnimationRepeat]
 * @param onEnd run on [Animation.AnimationListener.onAnimationEnd]
 * @return receiver
 */
inline fun <T : Animation> T.listener(crossinline onStart: AnimCallback = {},
                                      crossinline onRepeat: AnimCallback = {},
                                      crossinline onEnd: AnimCallback = {}): T {
    setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            onStart(animation)
        }

        override fun onAnimationRepeat(animation: Animation) {
            onRepeat(animation)
        }

        override fun onAnimationEnd(animation: Animation) {
            onEnd(animation)
        }
    })
    return this
}
