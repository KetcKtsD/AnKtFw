package io.github.ketcktsd.anktfw.androidarch.croutine

import kotlinx.coroutines.*
import java.util.concurrent.*
import kotlin.coroutines.*

/**
 * Get commonPool with [coroutineContext]
 */
inline val CoroutineScope.commonPoolContext: CoroutineContext
    get() = coroutineContext + ForkJoinPool.commonPool().asCoroutineDispatcher()
