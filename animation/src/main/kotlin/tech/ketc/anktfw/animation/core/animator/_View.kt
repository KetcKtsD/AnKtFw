package tech.ketc.anktfw.animation.core.animator

import android.view.*

/**
 * create [ViewPropertyAnimator]
 *
 * @param init initialize [ViewPropertyAnimator]
 */
inline fun View.animate(init: ViewPropertyAnimator.() -> Unit): ViewPropertyAnimator = animate().apply {
    withLayer()
    init()
}
