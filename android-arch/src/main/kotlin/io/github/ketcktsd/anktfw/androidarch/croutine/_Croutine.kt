package io.github.ketcktsd.anktfw.androidarch.croutine

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import java.util.concurrent.ForkJoinPool
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.reflect.KClass

typealias DeferredResult<T> = Deferred<Result<T>>

private val DEFAULT_PREDICATE: (Throwable) -> Boolean = { true }

@Suppress("ResultIsResult")
internal inline fun <R> runOrThrowCatching(
        vararg expected: KClass<out Throwable>,
        block: () -> R
): Result<R> = try {
    Result.success(block())
} catch (t: Throwable) {
    if (expected.isEmpty() || expected.any { it.isInstance(t) }) Result.failure(t)
    else throw t
}

/**
 * Creates new coroutine and returns its future result as an implementation of [DeferredResult].
 *
 * @param R result object class
 * @param context context of the coroutine. The default value is [coroutineContext].
 * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
 * @param block the coroutine code.
 * @param expected expected exceptions, default value is emptyArray
 * @return DeferredResult
 */
fun <R> CoroutineScope.asyncResult(
        context: CoroutineContext = coroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        vararg expected: KClass<out Throwable> = emptyArray(),
        block: suspend CoroutineScope.() -> R
): DeferredResult<R> {
    suspend fun CoroutineScope.runCatching() = runOrThrowCatching(*expected) { block() }

    return async(context, start, CoroutineScope::runCatching)
}

/**
 * Creates new coroutine and returns its future result as an implementation of [DeferredResult].
 *
 * @param R result object class
 * @param context context of the coroutine. The default value is [coroutineContext].
 * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
 * @param block the coroutine code.
 * @param expected expected exceptions, default value is emptyArray
 * @param maxTimes is max retry times
 * @return DeferredResult
 * @throws IllegalArgumentException if give a negative number to [maxTimes]
 */
fun <R> CoroutineScope.asyncResult(
        context: CoroutineContext = coroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        vararg expected: KClass<out Throwable> = emptyArray(),
        maxTimes: Int,
        predicate: (Throwable) -> Boolean = DEFAULT_PREDICATE,
        block: suspend CoroutineScope.() -> R
): DeferredResult<R> {
    if (maxTimes < 0)
        throw IllegalArgumentException("give a negative number to maxTimes")

    suspend fun CoroutineScope.runCatching() = runOrThrowCatching(*expected) { block() }

    suspend fun CoroutineScope.retryCatching(): Result<R> {
        var retryCount = 0
        var result = runCatching()
        while (result.isFailure && retryCount < maxTimes) {
            retryCount++
            val exception = requireNotNull(result.exceptionOrNull())
            if (predicate(exception)) {
                result = runCatching()
            } else {
                return result
            }
        }
        return result
    }

    return async(context, start, CoroutineScope::retryCatching)
}

//bindLauncher
private fun createLifecycleObserver(job: Job) = object : LifecycleObserver {

    val mJobRef = WeakReference(job)

    @Suppress("unused")
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
