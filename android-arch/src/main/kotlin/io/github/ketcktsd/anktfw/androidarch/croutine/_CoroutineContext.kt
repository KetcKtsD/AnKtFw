package io.github.ketcktsd.anktfw.androidarch.croutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.ForkJoinPool
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

/**
 * Get commonPool with [coroutineContext]
 */
inline val CoroutineScope.defaultContext: CoroutineContext
    get() = coroutineContext + ForkJoinPool.commonPool().asCoroutineDispatcher()
