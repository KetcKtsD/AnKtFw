package tech.ketc.anktfw.animation.util

import android.content.*

private const val MIN_TABLET_WIDTH = 600

private const val MAX_WEARABLE_WIDTH = 250

private const val TABLET_DURATION_MAGNIFICATION = 1.3

private const val WEARABLE_DURATION_MAGNIFICATION = 0.7

/**
 * to determine if it is a Tablet Device
 *
 * @param context context
 */
fun isTablet(context: Context) =
        context.resources.configuration.smallestScreenWidthDp >= MIN_TABLET_WIDTH

/**
 * to determine if it is a Wearable Device
 *
 * @param context context
 */
fun isWearable(context: Context) =
        context.resources.configuration.smallestScreenWidthDp <= MAX_WEARABLE_WIDTH

/**
 * convert unit to dp for specified point
 *
 * @param context context
 * @param point point
 */
fun dp(context: Context, point: Float): Int {
    val density = context.resources.displayMetrics.density
    return (point * density).toInt()
}

/**
 * @see [dp]
 */
fun dp(context: Context, point: Int) = dp(context, point.toFloat())

/**
 * adjust duration by wearable device, tablet or not.
 *
 * @return adjusted duration
 */
fun adjustDuration(standardDuration: Long, context: Context) = when {
    isTablet(context) -> (standardDuration * TABLET_DURATION_MAGNIFICATION).toLong()
    isWearable(context) -> (standardDuration * WEARABLE_DURATION_MAGNIFICATION).toLong()
    else -> standardDuration
}
