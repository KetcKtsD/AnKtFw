package io.github.ketcktsd.anktfw.util

import android.content.*
import android.view.*
import androidx.drawerlayout.widget.*


@Suppress("ClassName")
class _DrawerLayout(ctx: Context) : DrawerLayout(ctx) {

    companion object {
        private val DEFAULT_INIT: LayoutParams.() -> Unit = {}
    }

    fun <T : View> T.lparams(width: Int, height: Int, init: LayoutParams.() -> Unit = DEFAULT_INIT) {
        layoutParams = LayoutParams(width, height).apply(init)
    }
}
