package tech.ketc.anktfw.androidarch

import android.annotation.SuppressLint
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import tech.ketc.anktfw.R
import tech.ketc.anktfw.util.UIComponent
import tech.ketc.anktfw.util.bindView

interface IAsyncSampleUI : UIComponent<AsyncSampleActivity, RelativeLayout> {
    val toolbar: Toolbar
    val button: Button
}

class AsyncSampleUI : IAsyncSampleUI {
    override lateinit var root: RelativeLayout
        private set

    private val toolbarId = View.generateViewId()
    override val toolbar: Toolbar by bindView(toolbarId)

    private val buttonId = View.generateViewId()
    override val button: Button by bindView(buttonId)

    @SuppressLint("SetTextI18n")
    override fun createView(ui: AnkoContext<AsyncSampleActivity>) = with(ui) {
        relativeLayout {
            root = this
            appBarLayout {
                this.toolbar {
                    id = toolbarId
                }
            }.lparams(matchParent, wrapContent)

            this.button {
                id = buttonId
                text = "start"
            }.lparams(wrapContent, wrapContent) {
                centerInParent()
            }
        }
    }
}