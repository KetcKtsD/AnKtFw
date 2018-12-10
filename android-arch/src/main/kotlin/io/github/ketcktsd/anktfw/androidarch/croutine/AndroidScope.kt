package io.github.ketcktsd.anktfw.androidarch.croutine

import kotlinx.coroutines.*
import kotlin.coroutines.*

object AndroidScope : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}
