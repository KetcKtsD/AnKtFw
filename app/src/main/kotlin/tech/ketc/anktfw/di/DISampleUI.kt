package tech.ketc.anktfw.di

import android.graphics.Color
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import org.jetbrains.anko.design.appBarLayout
import tech.ketc.anktfw.util.UIComponent
import tech.ketc.anktfw.util.bindView
import android.widget.ImageView.ScaleType
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

interface IDISampleUI : UIComponent<DISampleActivity, RelativeLayout> {
    val image: ImageView
    val toolbar: Toolbar
}

class DISampleUI : IDISampleUI {
    override lateinit var root: RelativeLayout
        private set

    private val toolbarId = View.generateViewId()
    override val toolbar: Toolbar by bindView(toolbarId)

    private val imageId = View.generateViewId()
    override val image: ImageView by bindView(imageId)

    override fun createView(ui: AnkoContext<DISampleActivity>) = with(ui) {
        relativeLayout {
            root = this
            appBarLayout {
                toolbar {
                    id = toolbarId
                }
            }.lparams(matchParent, wrapContent)

            imageView {
                id = imageId
                scaleType = ScaleType.CENTER_INSIDE
                backgroundColor = Color.parseColor("#FFFFFF")
            }.lparams(matchParent, matchParent) {
                centerInParent()
            }
        }
    }

}