package io.github.ketcktsd.anktfw.androidarch.croutine

import kotlinx.coroutines.*

interface LifecycleScopeSupport {
    val scope: LifecycleScope
}

fun LifecycleScopeSupport.bindLaunch(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
) = scope.bindLaunch(start, block)
