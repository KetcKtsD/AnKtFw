package tech.ketc.anktfw.androidarch

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.*
import tech.ketc.anktfw.AppbarComponent
import tech.ketc.anktfw.anko.UI
import tech.ketc.anktfw.anko.bindView
import tech.ketc.anktfw.anko.component

interface IAsyncSampleUI : UI<AsyncSampleActivity, RelativeLayout> {
    val toolbar: Toolbar
    val button: Button
}

class AsyncSampleUI : IAsyncSampleUI {
    override lateinit var root: RelativeLayout
        private set

    private val appbarComponent = AppbarComponent()
    override val toolbar: Toolbar by lazy { appbarComponent.toolbar }

    private val buttonId = View.generateViewId()
    override val button: Button by bindView(buttonId)

    @SuppressLint("SetTextI18n")
    override fun createView(ui: AnkoContext<AsyncSampleActivity>) = with(ui) {
        relativeLayout {
            root = this

            component(appbarComponent)
                    .lparams(matchParent, wrapContent)

            this.button {
                id = buttonId
                text = "start"
            }.lparams(wrapContent, wrapContent) {
                centerInParent()
            }
        }
    }
}