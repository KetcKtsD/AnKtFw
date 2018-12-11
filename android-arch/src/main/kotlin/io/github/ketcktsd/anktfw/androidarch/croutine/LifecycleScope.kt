package io.github.ketcktsd.anktfw.androidarch.croutine

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlin.coroutines.*

interface LifecycleScope : CoroutineScope

private class LifecycleScopeImpl(
        private val lifecycleOwner: LifecycleOwner,
        coroutineScope: CoroutineScope
) : LifecycleScope, LifecycleObserver {

    init {
        coroutineScope.coroutineContext[Job]
                ?.let { throw IllegalArgumentException("A Context with Job already passed") }

        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val mJob = SupervisorJob()

    override val coroutineContext: CoroutineContext = (coroutineScope + mJob).coroutineContext

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mJob.cancelChildren()
        lifecycleOwner.lifecycle.removeObserver(this)
    }
}

@Suppress("FunctionName")
fun LifecycleScope(
        lifecycleOwner: LifecycleOwner,
        coroutineScope: CoroutineScope = AndroidScope
): LifecycleScope = LifecycleScopeImpl(lifecycleOwner, coroutineScope)

@Suppress("NOTHING_TO_INLINE")
inline fun LifecycleScope.bindLaunch(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        noinline block: suspend CoroutineScope.() -> Unit
) = launch(coroutineContext, start, block)
