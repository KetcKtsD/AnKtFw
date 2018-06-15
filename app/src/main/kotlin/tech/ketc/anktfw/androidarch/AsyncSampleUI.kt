package tech.ketc.anktfw.androidarch

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.*
import tech.ketc.anktfw.util.UIComponent
import tech.ketc.anktfw.util.appbarLayout
import tech.ketc.anktfw.util.toolbar
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
            appbarLayout {
                toolbar {
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