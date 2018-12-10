package io.github.ketcktsd.anktfw.util

import com.google.android.material.navigation.*

var NavigationView.menuId: Int
    set(value) = inflateMenu(value)
    get() {
        throw UnsupportedOperationException()
    }
