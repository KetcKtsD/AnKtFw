package io.github.ketcktsd.anktfw

import android.content.*
import android.view.*
import androidx.appcompat.widget.*
import com.google.android.material.appbar.*
import io.github.ketcktsd.anktfw.anko.*
import io.github.ketcktsd.anktfw.util.*
import kotlin.properties.*

interface AppbarComponent : ViewComponent<AppBarLayout> {

    val toolbar: Toolbar
}

class SimpleAppbarComponent : AppbarComponent {
    override var root: AppBarLayout by Delegates.notNull()
        private set

    private val toolbarId = View.generateViewId()
    override val toolbar: Toolbar by bindView(toolbarId)

    override fun createView(ctx: Context) = with(ctx) {
        appbarLayout {
            root = this
            toolbar {
                id = toolbarId
            }
        }
    }
}
