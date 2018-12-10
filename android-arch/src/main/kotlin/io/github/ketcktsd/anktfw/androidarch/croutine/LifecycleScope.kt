package io.github.ketcktsd.anktfw.androidarch.croutine

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlin.coroutines.*

interface LifecycleScope : LifecycleObserver, CoroutineScope

private class LifecycleScopeImpl(
        private val lifecycleOwner: LifecycleOwner,
        private val coroutineScope: CoroutineScope
) : LifecycleScope {

    private val mJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = coroutineScope.coroutineContext + mJob

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

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

interface LifecycleScopeSupport {
    val scope: LifecycleScope
}

fun LifecycleScope.bindLaunch(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
) = launch(coroutineContext, start, block)

fun LifecycleScopeSupport.bindLaunch(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
) = scope.bindLaunch(start, block)
