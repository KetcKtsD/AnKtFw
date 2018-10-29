package io.github.ketcktsd.anktfw.bind

import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import io.github.ketcktsd.anktfw.AppbarComponent
import io.github.ketcktsd.anktfw.SimpleAppbarComponent
import io.github.ketcktsd.anktfw.anko.UI
import io.github.ketcktsd.anktfw.anko.bindView
import io.github.ketcktsd.anktfw.anko.component
import org.jetbrains.anko.*
import kotlin.properties.Delegates

interface IBindSampleUI : UI<BindSampleActivity, RelativeLayout> {
    val toolbar: Toolbar
    val editText: EditText
    val textView: TextView
    val lengthTextView: TextView
}

class BindSampleUI : IBindSampleUI {
    override var root: RelativeLayout by Delegates.notNull()
        private set

    private val appbarComponent: AppbarComponent = SimpleAppbarComponent()

    override val toolbar: Toolbar by lazy { appbarComponent.toolbar }

    private val editTextId = View.generateViewId()
    override val editText: EditText by bindView(editTextId)

    private val textViewId = View.generateViewId()
    override val textView: TextView  by bindView(textViewId)

    private val lengthTextViewId = View.generateViewId()
    override val lengthTextView: TextView by bindView(lengthTextViewId)

    override fun createView(ui: AnkoContext<BindSampleActivity>) = with(ui) {
        relativeLayout {
            root = this

            component(appbarComponent).lparams(matchParent, wrapContent) {
                bottomMargin = dip(96)
            }

            relativeLayout {
                textView {
                    id = lengthTextViewId
                    text = "0"
                }.lparams(matchParent, wrapContent) {
                    bottomMargin = dip(4)
                }

                editText {
                    id = editTextId
                }.lparams(matchParent, wrapContent) {
                    below(lengthTextViewId)
                    bottomMargin = dip(8)
                }

                textView {
                    id = textViewId
                }.lparams(matchParent, wrapContent) {
                    below(editTextId)
                }
            }.lparams(matchParent, matchParent) {
                below(appbarComponent.root)
                margin = dip(16)
            }
        }
    }
}
