package io.github.ketcktsd.anktfw.util

import com.google.android.material.navigation.NavigationView

var NavigationView.menuId: Int
    set(value) = inflateMenu(value)
    get() {
        throw UnsupportedOperationException()
    }
