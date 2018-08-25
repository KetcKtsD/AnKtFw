package io.github.ketcktsd.anktfw.animation.template

import android.content.Context
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import io.github.ketcktsd.anktfw.animation.core.animator.*
import io.github.ketcktsd.anktfw.animation.util.adjustDuration


private fun ViewPropertyAnimator.defaultInit(context: Context) {
    duration = adjustDuration(275, context)
    interpolator = FastOutSlowInInterpolator()
}

private fun ViewPropertyAnimator.defaultInitDelay(context: Context) {
    duration = adjustDuration(350, context)
    startDelay = adjustDuration(25, context)
    interpolator = FastOutSlowInInterpolator()
}

fun View.expandMtrl(toX: Float, toY: Float,
                    onStart: AnimatorCallback = {}, onEnd: AnimatorCallback = {}) {
    val animate1 = animate {
        toXScale = toX
        defaultInit(context)
    }
    val animate2 = animate {
        toYScale = toY
        defaultInitDelay(context)
    }
    animate1.withStartAction { animate2.listener(onStart = onStart, onEnd = onEnd).start() }.start()
}

fun View.collapseMtrl(toX: Float = 1f, toY: Float = 1f,
                      onStart: AnimatorCallback = {}, onEnd: AnimatorCallback = {}) {
    val animate1 = animate {
        toYScale = toY
        defaultInit(context)
    }
    val animate2 = animate {
        toXScale = toX
        defaultInitDelay(context)
    }
    animate1.withStartAction { animate2.listener(onStart = onStart, onEnd = onEnd).start() }.start()
}
