package io.github.ketcktsd.anktfw.androidarch.lifecycle

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.UI
import io.github.ketcktsd.anktfw.androidarch.croutine.bindLaunch
import kotlin.coroutines.CoroutineContext

/**
 * @see bindLaunch
 */
fun LifecycleOwner.bindLaunch(context: CoroutineContext = UI, start: CoroutineStart = CoroutineStart.DEFAULT,
                              parent: Job? = null,
                              block: suspend CoroutineScope.() -> Unit) = bindLaunch(this, context, start, parent, block)
