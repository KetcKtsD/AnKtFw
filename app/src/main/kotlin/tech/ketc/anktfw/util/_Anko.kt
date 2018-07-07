package tech.ketc.anktfw.util

import android.content.Context
import android.view.View
import android.view.ViewManager
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
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

fun ViewManager.drawerLayout(theme: Int = 0, init: _DrawerLayout.() -> Unit = DEFAULT_INIT): _DrawerLayout {
    return customView(theme, init)
}


fun Context.drawerLayout(theme: Int = 0, init: _DrawerLayout.() -> Unit = DEFAULT_INIT): _DrawerLayout {
    return customView(theme, init)
}


fun ViewManager.navigationView(theme: Int = 0, init: NavigationView.() -> Unit = DEFAULT_INIT): NavigationView {
    return customView(theme, init)
}


fun Context.navigationView(theme: Int = 0, init: NavigationView.() -> Unit = DEFAULT_INIT): NavigationView {
    return customView(theme, init)
}