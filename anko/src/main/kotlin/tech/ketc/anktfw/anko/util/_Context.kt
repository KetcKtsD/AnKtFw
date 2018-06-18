package tech.ketc.anktfw.anko.util

import android.content.Context
import android.util.TypedValue
import android.view.View

fun <R : View> Context.view(create: Context.() -> R): R = create()

/**
 * get resource ID of theme attribute
 */
fun Context.getResourceId(resId: Int): Int {
    val outValue = TypedValue()
    theme.resolveAttribute(resId, outValue, true)
    return outValue.resourceId
}