package io.github.ketcktsd.anktfw.androidarch.croutine

import kotlinx.coroutines.*

interface LifecycleScopeSupport {
    val scope: LifecycleScope
}

@Suppress("NOTHING_TO_INLINE")
inline fun LifecycleScopeSupport.bindLaunch(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        noinline block: suspend CoroutineScope.() -> Unit
) = scope.bindLaunch(start, block)