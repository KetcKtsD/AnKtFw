package tech.ketc.anktfw.anko.util

import android.graphics.drawable.Drawable
import org.jetbrains.anko.AnkoContext

/**
 * get drawable by id
 */
fun AnkoContext<*>.drawable(id: Int): Drawable = ctx.getDrawable(id)

/**
 * get color by id
 */
fun AnkoContext<*>.color(id: Int): Int = ctx.getColor(id)

/**
 * get string by id
 */
fun AnkoContext<*>.string(id: Int): String = ctx.getString(id)

/**
 * get id for theme attribute id
 */
fun AnkoContext<*>.resourceId(id: Int): Int = ctx.getResourceId(id)