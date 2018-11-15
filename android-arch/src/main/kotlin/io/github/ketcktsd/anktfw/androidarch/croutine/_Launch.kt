package io.github.ketcktsd.anktfw.androidarch.croutine

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

/**
 * Start [Job] bound to [LifecycleOwner]
 * @param owner target of bind
 * @param context context of the coroutine. The default value is [Dispatchers.Main].
 * @param block the coroutine code.
 */
fun bindLaunch(owner: LifecycleOwner,
               context: CoroutineContext = Dispatchers.Main,
               block: suspend CoroutineScope.() -> Unit): Job {
    val job = GlobalScope.launch(context, CoroutineStart.LAZY, block)
    val observer = createLifecycleObserver(job)
    val lifecycle = owner.lifecycle
    lifecycle.addObserver(observer)
    job.invokeOnCompletion { lifecycle.removeObserver(observer) }
    job.start()
    return job
}

private fun createLifecycleObserver(job: Job) = object : LifecycleObserver {

    val mJobRef = WeakReference(job)

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        val j = mJobRef.get() ?: return
        j.cancel()
    }
}