package io.github.ketcktsd.anktfw.animation

import android.os.*
import android.view.*
import androidx.appcompat.app.*
import io.github.ketcktsd.anktfw.androidarch.croutine.*
import io.github.ketcktsd.anktfw.androidarch.lifecycle.*
import io.github.ketcktsd.anktfw.animation.core.animator.*
import kotlinx.coroutines.*
import org.jetbrains.anko.*

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
