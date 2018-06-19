package tech.ketc.anktfw.di

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.*
import tech.ketc.anktfw.AppbarComponent
import tech.ketc.anktfw.anko.UI
import tech.ketc.anktfw.anko.bindView
import tech.ketc.anktfw.anko.component

interface IDISampleUI : UI<DISampleActivity, RelativeLayout> {
    val image: ImageView
    val toolbar: Toolbar
}

class DISampleUI : IDISampleUI {
    override lateinit var root: RelativeLayout
        private set

    private val appbarComponent = AppbarComponent()

    override val toolbar: Toolbar by lazy { appbarComponent.toolbar }

    private val imageId = View.generateViewId()
    override val image: ImageView by bindView(imageId)

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