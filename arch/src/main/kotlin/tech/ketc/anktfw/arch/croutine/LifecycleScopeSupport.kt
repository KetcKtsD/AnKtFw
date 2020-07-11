package tech.ketc.anktfw.arch.croutine

import kotlinx.coroutines.*

interface LifecycleScopeSupport {
    val scope: LifecycleScope
}

@Suppress("NOTHING_TO_INLINE", "unused")
inline fun LifecycleScopeSupport.bindLaunch(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        noinline block: suspend CoroutineScope.() -> Unit
) = scope.bindLaunch(start, block)

@Suppress("NOTHING_TO_INLINE", "unused")
inline fun <T> LifecycleScopeSupport.channel() = scope.channel<T>()

@Suppress("NOTHING_TO_INLINE", "unused")
inline fun <T> LifecycleScopeSupport.channel(capacity: Int) = scope.channel<T>(capacity)
