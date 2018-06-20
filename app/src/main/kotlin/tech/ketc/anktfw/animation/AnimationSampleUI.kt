package tech.ketc.anktfw.animation

import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView
import tech.ketc.anktfw.AppbarComponent
import tech.ketc.anktfw.SimpleAppbarComponent
import tech.ketc.anktfw.anko.UI
import tech.ketc.anktfw.anko.bindView
import tech.ketc.anktfw.anko.component
import kotlin.properties.Delegates

interface IAnimationSampleUI : UI<AnimationSampleActivity, RelativeLayout> {
    val toolbar: Toolbar
    val scaleView: View
    val fadeView: View
}

class AnimationSampleUI : IAnimationSampleUI {
    override var root: RelativeLayout by Delegates.notNull()
        private set

    private val appbarComponent: AppbarComponent = SimpleAppbarComponent()

    override val toolbar: Toolbar by lazy { appbarComponent.toolbar }

    private val fadeViewId = View.generateViewId()
    override val fadeView: View by bindView(fadeViewId)

    private val scaleViewId = View.generateViewId()
    override val scaleView: View by bindView(scaleViewId)

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

            component(animationViewComponent(scaleViewId)).lparams(dip(100), dip(100)) {
                below(fadeViewId)
                centerHorizontally()
            }
        }
    }
}
