package tech.ketc.anktfw.androidarch.lifecycle

import android.arch.lifecycle.LifecycleOwner
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import tech.ketc.anktfw.androidarch.croutine.bindLaunch
import kotlin.coroutines.experimental.CoroutineContext

/**
 * @see bindLaunch
 */
fun LifecycleOwner.bindLaunch(context: CoroutineContext = UI, start: CoroutineStart = CoroutineStart.DEFAULT,
                              parent: Job? = null,
                              block: suspend CoroutineScope.() -> Unit) = bindLaunch(this, context, start, parent, block)
