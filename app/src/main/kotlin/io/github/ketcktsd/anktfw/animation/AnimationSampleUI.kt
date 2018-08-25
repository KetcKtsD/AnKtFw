package io.github.ketcktsd.anktfw.animation

import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView
import io.github.ketcktsd.anktfw.AppbarComponent
import io.github.ketcktsd.anktfw.SimpleAppbarComponent
import io.github.ketcktsd.anktfw.anko.UI
import io.github.ketcktsd.anktfw.anko.bindView
import io.github.ketcktsd.anktfw.anko.component
import kotlin.properties.Delegates

interface IAnimationSampleUI : UI<AnimationSampleActivity, RelativeLayout> {
    val toolbar: Toolbar
    val fadeView: View
}

class AnimationSampleUI : IAnimationSampleUI {
    override var root: RelativeLayout by Delegates.notNull()
        private set

    private val appbarComponent: AppbarComponent = SimpleAppbarComponent()

    override val toolbar: Toolbar by lazy { appbarComponent.toolbar }

    private val fadeViewId = View.generateViewId()
    override val fadeView: View by bindView(fadeViewId)

    private fun animationViewComponent(viewId: Int) = component {
        customView<CardView> {
            id = viewId
        }
    }

    override fun createView(ui: AnkoContext<AnimationSampleActivity>) = with(ui) {
        relativeLayout {
            root = this
            component(appbarComponent).lparams(matchParent, wrapContent) {
                bottomMargin = dip(96)
            }

            component(animationViewComponent(fadeViewId)).lparams(dip(100), dip(100)) {
                below(appbarComponent.root)
                centerHorizontally()
                bottomMargin = dip(96)
            }
        }
    }
}
