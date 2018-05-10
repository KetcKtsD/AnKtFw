package tech.ketc.anktfw.androidarch.croutine

import android.arch.lifecycle.*
import android.util.Log
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import java.lang.ref.WeakReference
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.coroutineContext
import kotlin.reflect.KClass

typealias DeferredResponse<R> = Deferred<Response<R>>

internal inline fun <R> generateResponse(vararg expected: KClass<out Throwable> = arrayOf(Throwable::class),
                                         block: () -> R): Response<R> = try {
    Success(block())
} catch (t: Throwable) {
    if (expected.any { it.isInstance(t) })
        Failure(t)
    else throw t
}


/**
 * Creates new coroutine and returns its future result as an implementation of [DeferredResponse].
 *
 * @param R result object class
 * @param context context of the coroutine. The default value is [DefaultDispatcher].
 * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
 * @param parent explicitly specifies the parent job, overrides job from the [context] (if any).*
 * @param block the coroutine code.
 * @param expected expected exceptions, default value is only [Throwable]
 * @return DeferredResponse
 */
fun <R> asyncResponse(context: CoroutineContext,
                      start: CoroutineStart = CoroutineStart.DEFAULT,
                      parent: Job? = null,
                      vararg expected: KClass<out Throwable> = arrayOf(Throwable::class),
                      block: suspend () -> R): DeferredResponse<R> =
        async(context, start, parent) { generateResponse(*expected) { block() } }


//bindLauncher
private fun createLifecycleObserver(job: Job) = object : LifecycleObserver {
    val mRef = WeakReference<Job>(job)
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        val j = mRef.get() ?: return
        val completed = j.isCompleted
        if (!completed) {
            j.cancel()
        }
    }
}

/**
 * Start [Job] bound to [LifecycleOwner]
 * @param owner target of bind
 * @param context context of the coroutine. The default value is [DefaultDispatcher].
 * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
 * @param parent explicitly specifies the parent job, overrides job from the [context] (if any).
 * @param block the coroutine code.
 */
fun bindLaunch(owner: LifecycleOwner, context: CoroutineContext = UI, start: CoroutineStart = CoroutineStart.DEFAULT,
               parent: Job? = null,
               block: suspend CoroutineScope.() -> Unit) = launch(context, start, parent, block).apply {
    val observer = createLifecycleObserver(this)
    val lifecycle = owner.lifecycle
    lifecycle.addObserver(observer)
    invokeOnCompletion { lifecycle.removeObserver(observer) }
}

/**
 * Get [CommonPool] with [coroutineContext]
 */
val CoroutineScope.defaultContext: CoroutineContext
    get() = coroutineContext + CommonPool