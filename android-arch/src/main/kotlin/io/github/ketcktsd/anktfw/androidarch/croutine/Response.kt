package io.github.ketcktsd.anktfw.androidarch.croutine

/**
 * Response such as async processing
 * @param R something process result type
 */
sealed class Response<R>

/**
 * Class representing success of something
 * @property result something process result object
 */
data class Success<R>(val result: R) : Response<R>()

/**
 *  Class representing success of something
 *  @property error exception that occurred during some process
 */
data class Failure<R>(val error: Throwable) : Response<R>()

//extensions

/**
 * @return  Cast to [Success] if successful,  null otherwise
 */
fun <R> Response<R>.success(): Success<R>? = this as? Success

/**
 * @return  Cast to [Failure] if failed,  null otherwise
 */
fun <R> Response<R>.failure(): Failure<R>? = this as? Failure

/**
 * If it is [Success], run do [handle]
 *
 * @param handle if it succeeded
 */
inline fun <R> Response<R>.successIf(handle: (R) -> Unit) {
    val result = success()?.result ?: return
    handle(result)
}

/**
 * If it is [Failure], run do [handle]
 *
 * @param handle if it failed
 */
inline fun <R> Response<R>.failureIf(handle: (Throwable) -> Unit) {
    val throwable = failure()?.error ?: return
    handle(throwable)
}


/**
 * If it is [Success] and nonnull, return the [Success.result], otherwise return the [default] result.
 *
 * @param default generate default value
 */
fun <R> Response<R>.getOrElse(default: () -> R): R = success()?.result ?: default()


/**
 * Transform the result to an arbitrary form.
 *
 * @param transform transformation processing
 */
inline fun <R, T> Success<R>.map(transform: (R) -> T): T = transform(result)
