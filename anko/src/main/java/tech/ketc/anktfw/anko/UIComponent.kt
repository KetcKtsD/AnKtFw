package tech.ketc.anktfw.anko

import android.app.Activity
import android.view.View
import org.jetbrains.anko.AnkoComponent

interface UIComponent<A : Activity, R : View> : AnkoComponent<A> {
    val root: R
}

fun <A : Activity, R : View, T : View> UIComponent<A, R>.bindView(id: Int) = lazy {
    root.findViewById<T>(id)
}

val <A : Activity, R : View> UIComponent<A, R>.rootId: Int
    get() {
        if (root.id == View.NO_ID) {
            root.id = View.generateViewId()
        }
        return root.id
    }