package io.github.ketcktsd.anktfw.animation.core.animator

import android.view.View
import android.view.ViewPropertyAnimator

/**
 * create [ViewPropertyAnimator]
 *
 * @param init initialize [ViewPropertyAnimator]
 */
fun View.animate(init: ViewPropertyAnimator.() -> Unit): ViewPropertyAnimator = animate().apply {
    withLayer()
    init()
}
