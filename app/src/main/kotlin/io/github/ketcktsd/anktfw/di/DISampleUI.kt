package io.github.ketcktsd.anktfw.di

import android.graphics.*
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
import io.github.ketcktsd.anktfw.*
import io.github.ketcktsd.anktfw.anko.*
import org.jetbrains.anko.*

interface IDISampleUI : UI<DISampleActivity, RelativeLayout> {
    val imageView: ImageView
    val toolbar: Toolbar
}

class DISampleUI : IDISampleUI {
    override lateinit var root: RelativeLayout
        private set

    private val appbarComponent = SimpleAppbarComponent()

    override val toolbar: Toolbar by lazy { appbarComponent.toolbar }

    private val imageId = View.generateViewId()
    override val imageView: ImageView by bindView(imageId)

    override fun createView(ui: AnkoContext<DISampleActivity>) = with(ui) {
        relativeLayout {
            root = this
            component(appbarComponent).lparams(matchParent, wrapContent)

            imageView {
                id = imageId
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                backgroundColor = Color.parseColor("#00FFFFFF")
            }.lparams(matchParent, matchParent) {
                centerInParent()
            }
        }
    }
}
