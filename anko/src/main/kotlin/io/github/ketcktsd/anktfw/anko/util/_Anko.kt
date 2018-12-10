package io.github.ketcktsd.anktfw.anko.util

import android.graphics.drawable.*
import org.jetbrains.anko.*

/**
 * get drawable by id
 */
fun AnkoContext<*>.drawable(id: Int): Drawable = requireNotNull(ctx.getDrawable(id))

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
