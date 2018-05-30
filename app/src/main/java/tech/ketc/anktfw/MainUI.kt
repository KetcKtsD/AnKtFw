package tech.ketc.anktfw

import android.annotation.SuppressLint
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import tech.ketc.anktfw.util.UIComponent
import tech.ketc.anktfw.util.bindView

interface IMainUI : UIComponent<MainActivity, RelativeLayout> {
    val textView: TextView
    val toolbar: Toolbar
}

class MainUI : IMainUI {
    override lateinit var root: RelativeLayout
        private set

    private val textViewId = View.generateViewId()
    override val textView: TextView  by bindView(textViewId)

    private val toolbarId = View.generateViewId()
    override val toolbar: Toolbar by bindView(toolbarId)

    @SuppressLint("SetTextI18n")
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        relativeLayout {
            root = this

            appBarLayout {
                toolbar {
                    id = toolbarId
                }
            }.lparams(matchParent, wrapContent)

            this.textView {
                id = textViewId
                text = "Hello World"
            }.lparams(wrapContent, wrapContent) {
                centerInParent()
            }
        }
    }
}