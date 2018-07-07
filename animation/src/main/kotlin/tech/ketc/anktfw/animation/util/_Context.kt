package tech.ketc.anktfw.animation.util

import android.content.Context

val Context.isTablet: Boolean
    get() = isTablet(this)

val Context.isWearable: Boolean
    get() = isWearable(this)

fun Context.dp(point: Float) = dp(this, point)

fun Context.dp(point: Int) = dp(this, point)

fun Context.adjustDuration(standardDuration: Long) = adjustDuration(standardDuration, this)