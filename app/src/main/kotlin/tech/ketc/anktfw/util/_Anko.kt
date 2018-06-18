package tech.ketc.anktfw.util

import android.content.Context
import android.view.View
import android.view.ViewManager
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import org.jetbrains.anko.custom.customView

private val DEFAULT_INIT: View.() -> Unit = {}

fun ViewManager.toolbar(theme: Int = 0, init: Toolbar.() -> Unit = DEFAULT_INIT): Toolbar {
    return customView(theme, init)
}

fun Context.toolbar(theme: Int = 0, init: Toolbar.() -> Unit = DEFAULT_INIT): Toolbar {
    return customView(theme, init)
}

fun ViewManager.appbarLayout(theme: Int = 0, init: AppBarLayout.() -> Unit = DEFAULT_INIT): AppBarLayout {
    return customView(theme, init)
}

fun Context.appbarLayout(theme: Int = 0, init: AppBarLayout.() -> Unit = DEFAULT_INIT): AppBarLayout {
    return customView(theme, init)
}
