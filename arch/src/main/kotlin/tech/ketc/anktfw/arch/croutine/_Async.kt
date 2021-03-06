package tech.ketc.anktfw.arch.croutine

import kotlinx.coroutines.*
import kotlin.coroutines.*
import kotlin.reflect.*

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
 * @param context context of the coroutine. The default value is [EmptyCoroutineContext].
 * @param start coroutine start option. The default value is [CoroutineStart.DEFAULT].
 * @param block the coroutine code.
 * @param expected expected exceptions, default value is emptyArray
 * @return [DeferredResult]
 */
fun <R> CoroutineScope.resultAsync(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        vararg expected: KClass<out Throwable> = emptyArray(),
        block: suspend CoroutineScope.() -> R
): DeferredResult<R> = async(context, start) {
    runOrThrowCatching(*expected) { block() }
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
fun <R> CoroutineScope.resultAsync(
        context: CoroutineContext = coroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        vararg expected: KClass<out Throwable> = emptyArray(),
        maxTimes: Int,
        predicate: (Throwable) -> Boolean = DEFAULT_PREDICATE,
        block: suspend CoroutineScope.() -> R
): DeferredResult<R> {
    if (maxTimes < 0)
        throw IllegalArgumentException("give a negative number to maxTimes")

    suspend fun retryCatching(): Result<R> {
        var retryCount = 0
        var result = runOrThrowCatching(*expected) { block() }
        while (result.isFailure && retryCount < maxTimes) {
            retryCount++
            val exception = requireNotNull(result.exceptionOrNull())
            if (predicate(exception)) {
                result = runOrThrowCatching(*expected) { block() }
            } else {
                return result
            }
        }
        return result
    }

    return async(context, start) { retryCatching() }
}
