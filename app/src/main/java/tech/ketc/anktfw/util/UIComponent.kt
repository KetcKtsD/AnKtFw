package tech.ketc.anktfw.util

import android.content.Context
import android.view.View
import org.jetbrains.anko.AnkoComponent

interface UIComponent<A : Context, R : View> : AnkoComponent<A> {
    val root: R
}

fun <A : Context, R : View, T : View> UIComponent<A, R>.bindView(id: Int) = lazy { root.findViewById<T>(id) }