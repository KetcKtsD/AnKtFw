package io.github.ketcktsd.anktfw.animation.core.anim

import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener

typealias AnimCallback = (anim:Animation) -> Unit


private val DEFAULT_CALLBACK: AnimCallback = {}

/**
 * set listener to animation
 *
 * @param onStart run on [Animation.AnimationListener.onAnimationStart]
 * @param onRepeat run on [Animation.AnimationListener.onAnimationRepeat]
 * @param onEnd run on [Animation.AnimationListener.onAnimationEnd]
 * @return receiver
 */
fun <T : Animation> T.listener(onStart: AnimCallback = DEFAULT_CALLBACK,
                               onRepeat: AnimCallback = DEFAULT_CALLBACK,
                               onEnd: AnimCallback = DEFAULT_CALLBACK): T {
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
