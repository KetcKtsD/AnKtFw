package tech.ketc.anktfw

import android.content.Context
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import tech.ketc.anktfw.anko.ViewComponent
import tech.ketc.anktfw.anko.bindView
import tech.ketc.anktfw.util.appbarLayout
import tech.ketc.anktfw.util.toolbar
import kotlin.properties.Delegates

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