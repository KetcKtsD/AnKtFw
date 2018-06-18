package tech.ketc.anktfw.util

import android.content.Context
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout


class _DrawerLayout(ctx: Context) : DrawerLayout(ctx) {

    companion object {
        private val DEFAULT_INIT: LayoutParams.() -> Unit = {}
    }

    fun <T : View> T.lparams(width: Int, height: Int, init: LayoutParams.() -> Unit = DEFAULT_INIT) {
        layoutParams = LayoutParams(width, height).apply(init)
    }
}