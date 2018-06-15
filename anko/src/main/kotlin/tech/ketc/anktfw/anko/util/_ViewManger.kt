package tech.ketc.anktfw.anko.util

import android.content.Context
import android.view.View
import android.view.ViewManager
import tech.ketc.anktfw.anko.component

fun <T : View> ViewManager.view(view: Context.() -> T): T = component(component { view() })