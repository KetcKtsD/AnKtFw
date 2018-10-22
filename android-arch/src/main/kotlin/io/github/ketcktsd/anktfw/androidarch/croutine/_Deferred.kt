package io.github.ketcktsd.anktfw.androidarch.croutine

import kotlinx.coroutines.Deferred

private val DEFAULT_PREDICATE: (Throwable) -> Boolean = { true }

/**
 * retry more than once [Deferred.await] until it succeeds depending on specific [predicate]
 *
 * @param maxTimes is max retry times
 * @param predicate retry when this function returns true.
 * default value is a function that returns true.
 */
suspend fun <T> DeferredResult<T>.retryAwait(
        maxTimes: Int,
        predicate: (Throwable) -> Boolean = DEFAULT_PREDICATE
): Result<T> {
    var retryCount = 0
    var result = await()
    while (result.isFailure && retryCount < maxTimes) {
        retryCount++
        val exception = requireNotNull(result.exceptionOrNull())
        if (predicate(exception)) {
            result = await()
        } else {
            return result
        }
    }
    return result
}
