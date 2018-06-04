package tech.ketc.anktfw.util

import android.app.Activity
import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.setContentView

interface UIComponent<A : Activity, R : View> : AnkoComponent<A> {
    val root: R
    fun A.setContentView() = this@UIComponent.setContentView(this)
}

fun <A : Activity, R : View, T : View> UIComponent<A, R>.bindView(id: Int) = lazy { root.findViewById<T>(id) }