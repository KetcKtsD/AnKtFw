package tech.ketc.anktfw.util

import android.view.ViewManager
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import tech.ketc.anktfw.anko.util.view

fun ViewManager.toolbar(init: Toolbar.() -> Unit): Toolbar {
    return view { Toolbar(this).apply(init) }
}

fun ViewManager.appbarLayout(init: AppBarLayout.() -> Unit): AppBarLayout {
    return view { AppBarLayout(this).apply(init) }
}