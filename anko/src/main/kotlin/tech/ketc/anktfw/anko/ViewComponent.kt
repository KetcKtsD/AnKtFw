package tech.ketc.anktfw.anko

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoException
import kotlin.properties.Delegates

/**
 * Interface for defining common components
 */
interface ViewComponent<R : View> : Component<R> {
    /**
     * create any [View]
     * @param ctx context
     */
    fun createView(ctx: Context): R
}


private val DEFAULT_INIT: View.() -> Unit = {}

/**
 * add [ViewComponent] to [ViewManager]
 *
 * @param component [ViewComponent]
 * @return created View
 */
fun <R : View> ViewManager.component(component: ViewComponent<R>,
                                     init: R.() -> Unit = DEFAULT_INIT): R {
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
fun <R : View> ViewManager.addComponent(component: ViewComponent<R>,
                                        init: R.() -> Unit = DEFAULT_INIT): R = component(component, init)

/**
 * for making simple UI component
 * @param  create create view
 * @return created Component
 */
fun <R : View> component(create: Context.() -> R): ViewComponent<R> = object : ViewComponent<R> {
    override var root: R by Delegates.notNull()
        private set

    override fun createView(ctx: Context) = ctx.create().also { root = it }
}