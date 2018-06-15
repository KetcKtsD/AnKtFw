package tech.ketc.anktfw

import android.annotation.SuppressLint
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.*
import tech.ketc.anktfw.anko.UI
import tech.ketc.anktfw.anko.bindView

import tech.ketc.anktfw.util.appbarLayout
import tech.ketc.anktfw.util.toolbar

interface IMainUI : UI<MainActivity, RelativeLayout> {
    val textView: TextView
    val toolbar: Toolbar
}

class MainUI : IMainUI {
    override lateinit var root: RelativeLayout
        private set

    private val textViewId = View.generateViewId()
    override val textView: TextView by bindView(textViewId)

    private val toolbarId = View.generateViewId()
    override val toolbar: Toolbar by bindView(toolbarId)

    @SuppressLint("SetTextI18n")
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        relativeLayout {
            root = this

            appbarLayout {
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