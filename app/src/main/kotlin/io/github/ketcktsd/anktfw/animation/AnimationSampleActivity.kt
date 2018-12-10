package io.github.ketcktsd.anktfw.animation

import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.appcompat.app.AppCompatActivity
import io.github.ketcktsd.anktfw.androidarch.croutine.*
import io.github.ketcktsd.anktfw.androidarch.lifecycle.IOnActiveRunner
import io.github.ketcktsd.anktfw.androidarch.lifecycle.OnActiveRunner
import io.github.ketcktsd.anktfw.animation.core.animator.animate
import io.github.ketcktsd.anktfw.animation.core.animator.fromAlpha
import io.github.ketcktsd.anktfw.animation.core.animator.toAlpha
import kotlinx.coroutines.delay
import org.jetbrains.anko.setContentView

class AnimationSampleActivity : AppCompatActivity(),
        LifecycleScopeSupport,
        IOnActiveRunner by OnActiveRunner(),
        IAnimationSampleUI by AnimationSampleUI() {

    companion object {
        private const val ANIMATION_DELAY_MILLS = 1000L
        private const val ANIMATION_DURATION = 300L
    }

    override val scope: LifecycleScope = LifecycleScope(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOwner(this)
        setContentView(this)
        setSupportActionBar(toolbar)
        fadeView.fadeOut()
    }

    private fun View.fadeIn(): ViewPropertyAnimator = animate {
        duration = ANIMATION_DURATION
        fromAlpha = 0f
        toAlpha = 1f
    }.withEndAction { delayFadeOut() }

    private fun View.fadeOut(): ViewPropertyAnimator = animate {
        duration = ANIMATION_DURATION
        fromAlpha = 1f
        toAlpha = 0f
    }.withEndAction { delayFadeIn() }

    private fun View.delayFadeOut() = bindLaunch {
        delay(ANIMATION_DELAY_MILLS)
        runOnActive { fadeOut() }
    }

    private fun View.delayFadeIn() = bindLaunch {
        delay(ANIMATION_DELAY_MILLS)
        runOnActive { fadeIn() }
    }
}
