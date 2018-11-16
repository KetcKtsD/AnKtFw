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
    val destroyer = createJobDestroyer(owner, job)
    val lifecycle = owner.lifecycle
    lifecycle.addObserver(destroyer)
    job.invokeOnCompletion { lifecycle.removeObserver(destroyer) }
    job.start()
    return job
}

private fun createJobDestroyer(owner: LifecycleOwner, job: Job) = object : LifecycleObserver {

    val mJobRef = WeakReference(job)
    val mOwnerRef = WeakReference(owner)


    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mOwnerRef.get()?.lifecycle?.removeObserver(this)
        mJobRef.get()?.cancel()
        mOwnerRef.clear()
        mJobRef.clear()
    }
}
