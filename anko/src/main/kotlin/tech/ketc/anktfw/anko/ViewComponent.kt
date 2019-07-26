package tech.ketc.anktfw.anko

import android.content.*
import android.view.*
import org.jetbrains.anko.*
import kotlin.properties.*

/**
 * interface for defining common component
 *
 * @param R root view
 */
interface ViewComponent<R : View> : Component<R> {
    /**
     * create any [View]
     * @param ctx context
     */
    fun createView(ctx: Context): R
}


/**
 * add [ViewComponent] to [ViewManager]
 *
 * @param component [ViewComponent]
 * @return created View
 */
inline fun <R : View> ViewManager.component(component: ViewComponent<R>,
                                            crossinline init: R.() -> Unit = {}): R {
    val initialize: R.() -> Unit = {
        init()
        if (id == View.NO_ID) id = View.generateViewId()
    }

    return when (this) {
        is ViewGroup -> component.createView(context).apply(initialize).also(::addView)
        is AnkoContext<*> -> component.createView(ctx).apply(initialize).also { addView(it, null) }
        else -> throw AnkoException("$this is the wrong parent")
    }
}

/**
 * @see [ViewManager.component]
 */
inline fun <R : View> ViewManager.addComponent(
        component: ViewComponent<R>,
        crossinline init: R.() -> Unit = {}
): R = component(component, init)

/**
 * for making simple UI component
 * @param  create create view
 * @return created Component
 */
inline fun <R : View> component(
        crossinline create: Context.() -> R
): ViewComponent<R> = object : ViewComponent<R> {
    override var root: R by Delegates.notNull()
        private set

    override fun createView(ctx: Context) = create(ctx).also { root = it }
}
