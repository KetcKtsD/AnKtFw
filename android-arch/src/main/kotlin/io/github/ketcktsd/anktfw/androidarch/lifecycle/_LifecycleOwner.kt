package io.github.ketcktsd.anktfw.androidarch.lifecycle

import androidx.lifecycle.LifecycleOwner
import io.github.ketcktsd.anktfw.androidarch.croutine.bindLaunch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * @see bindLaunch
 */
fun LifecycleOwner.bindLaunch(
        context: CoroutineContext = Dispatchers.Main,
        block: suspend CoroutineScope.() -> Unit
) = bindLaunch(this, context, block)
