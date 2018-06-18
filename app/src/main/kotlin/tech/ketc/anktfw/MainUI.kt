package tech.ketc.anktfw

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import org.jetbrains.anko.*
import tech.ketc.anktfw.anko.UI
import tech.ketc.anktfw.anko.ViewComponent
import tech.ketc.anktfw.anko.bindView
import tech.ketc.anktfw.anko.component

import tech.ketc.anktfw.util.appbarLayout
import tech.ketc.anktfw.util.toolbar
import kotlin.properties.Delegates

interface IAppbarComponent : ViewComponent<AppBarLayout> {
    val toolbar: Toolbar
}

class AppbarComponent : IAppbarComponent {
    override var root: AppBarLayout by Delegates.notNull()
        private set
    private val toolbarId = View.generateViewId()
    override val toolbar: Toolbar by bindView(toolbarId)
    override fun createView(ctx: Context) = ctx.appbarLayout {
        root = this
        toolbar {
            id = toolbarId
        }
    }
}

interface IMainUI : UI<MainActivity, RelativeLayout> {
    val textView: TextView
    val appbarComponent: IAppbarComponent
}

class MainUI : IMainUI {
    override lateinit var root: RelativeLayout
        private set

    private val textViewId = View.generateViewId()
    override val textView: TextView by bindView(textViewId)

    override val appbarComponent: IAppbarComponent = AppbarComponent()

    @SuppressLint("SetTextI18n")
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        relativeLayout {
            root = this

            component(appbarComponent)
                    .lparams(matchParent, wrapContent)

            this.textView {
                id = textViewId
                text = "Hello World"
            }.lparams(wrapContent, wrapContent) {
                centerInParent()
            }
        }
    }
}