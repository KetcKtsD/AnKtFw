package tech.ketc.anktfw.androidarch.croutine

/**
 * response such as async processing
 * @param R something process result type
 */
sealed class Response<R>

/**
 * class representing success of something
 * @property result something process result object
 */
data class Success<R>(val result: R) : Response<R>()

/**
 *  class representing success of something
 *  @property error exception that occurred during some process
 */
data class Failure<R>(val error: Throwable) : Response<R>()

//extensions

/**
 * @return  cast to [Success] if successful,  null otherwise
 */
fun <R> Response<R>.success(): Success<R>? = when {
    this is Success -> this
    else -> null
}

/**
 * @return  cast to [Failure] if failed,  null otherwise
 */
fun <R> Response<R>.failure(): Failure<R>? = when {
    this is Failure -> this
    else -> null
}

/**
 * if it is [Success], run do [handle]
 *
 * @param handle if it succeeded
 */
inline fun <R> Response<R>.successIf(handle: (R) -> Unit) {
    val result = success()?.result ?: return
    handle(result)
}

/**
 * if it is [Failure], run do [handle]
 *
 * @param handle if it failed
 */
inline fun <R> Response<R>.failureIf(handle: (Throwable) -> Unit) {
    val throwable = failure()?.error ?: return
    handle(throwable)
}


/**
 * if it is [Success], return the result, otherwise return the [default] result.
 *
 * @param default generate default value
 */
fun <R> Response<R>.getOrElse(default: () -> R): R = success()?.result ?: default()


/**
 * transform the result to an arbitrary form.
 *
 * @param transform transformation processing
 */
inline fun <R, T> Success<R>.map(transform: (R) -> T): T = transform(result)