package io.github.ketcktsd.anktfw.androidarch.croutine

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.invokeOnCompletion
import kotlinx.coroutines.android.UI
import kotlinx.coroutines.intrinsics.startCoroutineCancellable
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.reflect.KClass

typealias DeferredResult<T> = Deferred<SuccessOrFailure<T>>

internal inline fun <R> generateResult(vararg expected: KClass<out Throwable> = arrayOf(Throwable::class),
                                       block: () -> R): SuccessOrFailure<R> = try {
    SuccessOrFailure.success(block())
} catch (t: Throwable) {
    if (expected.any { it.isInstance(t) })
        SuccessOrFailure.failure(t)
    else throw t
}

/**
 * Creates new coroutine and returns its future result as an implementation of [DeferredResult].
 *
 * @param R result object class
 * @param context context of the coroutine. The default value is [DefaultDispatcher].
 * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
 * @param parent explicitly specifies the parent job, overrides job from the [context] (if any).*
 * @param block the coroutine code.
 * @param expected expected exceptions, default value is only [Throwable]
 * @return DeferredResult
 */
fun <R> execAsync(context: CoroutineContext,
                  start: CoroutineStart = CoroutineStart.DEFAULT,
                  parent: Job? = null,
                  vararg expected: KClass<out Throwable> = arrayOf(Throwable::class),
                  block: suspend () -> R): DeferredResult<R> =
        async(context, start, parent) { generateResult(*expected) { block() } }

//bindLauncher
private fun createLifecycleObserver(job: Job) = object : LifecycleObserver {

    val mJobRef = WeakReference(job)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        val j = mJobRef.get() ?: return
        j.cancel()
    }
}

private fun createLazyCoroutine(context: CoroutineContext,
                                block: suspend CoroutineScope.() -> Unit
) = object : AbstractCoroutine<Unit>(context, false) {
    override fun onStart() {
        block.startCoroutineCancellable(this, this)
    }
}

private fun createCoroutine(context: CoroutineContext) =
        object : AbstractCoroutine<Unit>(context, true) {}


/**
 * Start [Job] bound to [LifecycleOwner]
 * @param owner target of bind
 * @param context context of the coroutine. The default value is [DefaultDispatcher].
 * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
 * @param parent explicitly specifies the parent job, overrides job from the [context] (if any).
 * @param block the coroutine code.
 */
fun bindLaunch(owner: LifecycleOwner, context: CoroutineContext = UI,
               start: CoroutineStart = CoroutineStart.DEFAULT,
               parent: Job? = null,
               block: suspend CoroutineScope.() -> Unit): Job {
    val newContext = newCoroutineContext(context, parent)

    val coroutine = if (start.isLazy)
        createLazyCoroutine(newContext, block) else
        createCoroutine(newContext)

    val observer = createLifecycleObserver(coroutine)
    val lifecycle = owner.lifecycle
    lifecycle.addObserver(observer)
    invokeOnCompletion { lifecycle.removeObserver(observer) }
    coroutine.start(start, coroutine, block)
    return coroutine
}

/**
 * Get [CommonPool] with [coroutineContext]
 */
suspend inline fun defaultContext() = coroutineContext + CommonPool
