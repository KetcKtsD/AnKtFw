package tech.ketc.anktfw.anko

import android.view.View

/**
 * interface for defining component of ui
 *
 * @param R root view
 */
interface Component<R : View> {

    /**
     * root view
     */
    val root: R
}

fun <R : View, T : View> Component<R>.bindView(id: Int) = lazy {
    root.findViewById<T>(id)
}

val Component<*>.rootId: Int
    get() {
        if (root.id == View.NO_ID) {
            root.id = View.generateViewId()
        }
        return root.id
    }