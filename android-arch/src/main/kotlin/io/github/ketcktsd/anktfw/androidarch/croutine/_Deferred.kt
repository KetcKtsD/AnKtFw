package io.github.ketcktsd.anktfw.androidarch.croutine

import kotlinx.coroutines.Deferred

private val DEFAULT_PREDICATE: (Throwable) -> Boolean = { true }

/**
 * retry more than once [Deferred.await] depending on specific [predicate]
 * @param maxTimes is max retry times
 * @param predicate retry when this function returns true.
 * default value is a function that returns true.
 */
suspend fun <T> DeferredResult<T>.retryAwait(
        maxTimes: Int,
        predicate: (Throwable) -> Boolean = DEFAULT_PREDICATE
): SuccessOrFailure<T> {
    var retryCount = 0
    var result = await()
    while (result.isFailure && retryCount < maxTimes) {
        retryCount++
        if (predicate(requireNotNull(result.exceptionOrNull()))) {
            result = await()
        } else {
            return result
        }
    }
    return result
}
