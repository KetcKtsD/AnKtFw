package io.github.ketcktsd.anktfw.animation

import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.setContentView
import io.github.ketcktsd.anktfw.animation.core.animator.animate
import io.github.ketcktsd.anktfw.animation.core.animator.fromAlpha
import io.github.ketcktsd.anktfw.animation.core.animator.toAlpha
import io.github.ketcktsd.anktfw.animation.template.collapseMtrl
import io.github.ketcktsd.anktfw.animation.template.expandMtrl

class AnimationSampleActivity : AppCompatActivity(), IAnimationSampleUI by AnimationSampleUI() {

    private fun View.fadeIn(): ViewPropertyAnimator = animate {
        fromAlpha = 0f
        toAlpha = 1f
    }.withEndAction { this.fadeOut() }

    private fun View.fadeOut(): ViewPropertyAnimator = animate {
        fromAlpha = 1f
        toAlpha = 0f
    }.withEndAction { this.fadeIn() }

    private fun View.collapse(): Unit = collapseMtrl { expand() }

    private fun View.expand(): Unit = expandMtrl(1.5f, 1.5f) { collapse() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this)

        fadeView.fadeOut()

        scaleView.expand()
    }
}
