package tech.ketc.anktfw.arch.croutine

import kotlinx.coroutines.*
import kotlin.coroutines.*

/**
 * A Android main thread [CoroutineScope] not bound to any job.
 * Android Scope is used to launch on Android-main-thread coroutines which are operating on the whole application lifetime.
 */
object AndroidScope : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}
