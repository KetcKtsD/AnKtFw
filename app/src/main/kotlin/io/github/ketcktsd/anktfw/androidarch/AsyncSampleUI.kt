package io.github.ketcktsd.anktfw.androidarch

import android.annotation.*
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
import io.github.ketcktsd.anktfw.*
import io.github.ketcktsd.anktfw.anko.*
import org.jetbrains.anko.*

interface IAsyncSampleUI : UI<AsyncSampleActivity, RelativeLayout> {
    val toolbar: Toolbar
    val button: Button
}

class AsyncSampleUI : IAsyncSampleUI {
    override lateinit var root: RelativeLayout
        private set

    private val appbarComponent = SimpleAppbarComponent()
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
