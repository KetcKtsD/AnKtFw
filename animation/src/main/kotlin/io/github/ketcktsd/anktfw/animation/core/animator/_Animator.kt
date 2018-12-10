package io.github.ketcktsd.anktfw.animation.core.animator

import android.animation.*
import android.view.*

typealias AnimatorCallback = (Animator) -> Unit

/**
 * set listener to [ViewPropertyAnimator]
 *
 * @param onStart run on [Animator.AnimatorListener.onAnimationRepeat]
 * @param onCancel run on [Animator.AnimatorListener.onAnimationCancel]
 * @param onRepeat run on [Animator.AnimatorListener.onAnimationRepeat]
 * @param onEnd run on [Animator.AnimatorListener.onAnimationEnd]
 * @return receiver
 */
fun ViewPropertyAnimator.listener(onStart: AnimatorCallback = {},
                                  onCancel: AnimatorCallback = {},
                                  onRepeat: AnimatorCallback = {},
                                  onEnd: AnimatorCallback = {}): ViewPropertyAnimator {
    this.setListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            onStart(animation)
        }

        override fun onAnimationCancel(animation: Animator) {
            onCancel(animation)
        }

        override fun onAnimationRepeat(animation: Animator) {
            onRepeat(animation)
        }

        override fun onAnimationEnd(animation: Animator) {
            onEnd(animation)
        }
    })
    return this
}

/**
 * set listener to [Animator]
 *
 * @param onStart run on [Animator.AnimatorListener.onAnimationRepeat]
 * @param onCancel run on [Animator.AnimatorListener.onAnimationCancel]
 * @param onRepeat run on [Animator.AnimatorListener.onAnimationRepeat]
 * @param onEnd run on [Animator.AnimatorListener.onAnimationEnd]
 * @return receiver
 */
fun Animator.listener(onStart: AnimatorCallback = {},
                      onCancel: AnimatorCallback = {},
                      onRepeat: AnimatorCallback = {},
                      onEnd: AnimatorCallback = {}): Animator {
    this.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            onStart(animation)
        }

        override fun onAnimationCancel(animation: Animator) {
            onCancel(animation)
        }

        override fun onAnimationRepeat(animation: Animator) {
            onRepeat(animation)
        }

        override fun onAnimationEnd(animation: Animator) {
            onEnd(animation)
        }
    })
    return this
}
