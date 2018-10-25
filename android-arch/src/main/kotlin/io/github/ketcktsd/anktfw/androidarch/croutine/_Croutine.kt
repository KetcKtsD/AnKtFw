package io.github.ketcktsd.anktfw.androidarch.croutine

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException
import java.lang.ref.WeakReference
import java.util.concurrent.ForkJoinPool
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.reflect.KClass

typealias DeferredResult<T> = Deferred<Result<T>>

private val DEFAULT_PREDICATE: (Throwable) -> Boolean = { true }

internal inline fun <R> runOrThrowCatching(
        vararg expected: KClass<out Throwable> = arrayOf(Throwable::class),
        block: () -> R
): Result<R> = try {
    Result.success(block())
} catch (t: Throwable) {
    if (expected.any { it.isInstance(t) })
        Result.failure(t)
    else throw t
}

/**
 * Creates new coroutine and returns its future result as an implementation of [DeferredResult].
 *
 * @param R result object class
 * @param context context of the coroutine. The default value is [coroutineContext].
 * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
 * @param block the coroutine code.
 * @param expected expected exceptions, default value is only [Throwable]
 * @return DeferredResult
 */
fun <R> CoroutineScope.asyncResult(
        context: CoroutineContext = coroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        vararg expected: KClass<out Throwable> = arrayOf(Throwable::class),
        block: suspend () -> R
): DeferredResult<R> = async(context, start) { runOrThrowCatching(*expected) { block() } }

/**
 * Creates new coroutine and returns its future result as an implementation of [DeferredResult].
 *
 * @param R result object class
 * @param context context of the coroutine. The default value is [coroutineContext].
 * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
 * @param block the coroutine code.
 * @param expected expected exceptions, default value is only [Throwable]
 * @param maxTimes is max retry times
 * @return DeferredResult
 * @throws IllegalArgumentException if give a negative number to [maxTimes]
 */
fun <R> CoroutineScope.asyncResult(
        context: CoroutineContext = coroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        vararg expected: KClass<out Throwable> = arrayOf(Throwable::class),
        maxTimes: Int,
        predicate: (Throwable) -> Boolean = DEFAULT_PREDICATE,
        block: suspend () -> R
): DeferredResult<R> {
    if (maxTimes < 0) throw IllegalArgumentException()
    return async(context, start) {
        suspend fun runCatching() = runOrThrowCatching(*expected) { block() }
        var retryCount = 0
        var result = runCatching()
        while (result.isFailure && retryCount < maxTimes) {
            retryCount++
            val exception = requireNotNull(result.exceptionOrNull())
            if (predicate(exception)) {
                result = runCatching()
            } else {
                return@async result
            }
        }
        return@async result
    }
}

//bindLauncher
private fun createLifecycleObserver(job: Job) = object : LifecycleObserver {

    val mJobRef = WeakReference(job)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        val j = mJobRef.get() ?: return
        j.cancel()
    }
}

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

/**
 * Get [CommonPool] with [coroutineContext]
 */
suspend inline fun defaultContext() =
        coroutineContext + ForkJoinPool.commonPool().asCoroutineDispatcher()
