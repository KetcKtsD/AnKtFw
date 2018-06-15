package tech.ketc.anktfw.anko.util

import org.jetbrains.anko.AnkoContext

/**
 * get drawable by id
 */
fun AnkoContext<*>.drawable(id: Int) = ctx.getDrawable(id)

/**
 * get color by id
 */
fun AnkoContext<*>.color(id: Int) = ctx.getColor(id)

/**
 * get string by id
 */
fun AnkoContext<*>.string(id: Int) = ctx.getString(id)

/**
 * get id for theme attribute id
 */
fun AnkoContext<*>.resourceId(id: Int) = ctx.getResourceId(id)