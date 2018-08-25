package io.github.ketcktsd.anktfw.anko.util

import android.content.Context
import android.util.TypedValue

/**
 * get resource ID of theme attribute
 */
fun Context.getResourceId(resId: Int): Int {
    val outValue = TypedValue()
    theme.resolveAttribute(resId, outValue, true)
    return outValue.resourceId
}
