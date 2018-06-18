package tech.ketc.anktfw.anko

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoException

val ViewManager.context: Context
    get() = when (this) {
        is ViewGroup -> this.context
        is AnkoContext<*> -> this.ctx
        else -> throw AnkoException("$this is the wrong parent")
    }

fun <T : View> ViewManager.view(create: Context.() -> T): T = addComponent(component { create(context) })