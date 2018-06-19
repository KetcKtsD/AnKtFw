package tech.ketc.anktfw.di

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.asCoroutineDispatcher
import tech.ketc.anktfw.androidarch.croutine.DeferredResponse
import tech.ketc.anktfw.androidarch.croutine.asyncResponse
import tech.ketc.anktfw.di.module.InjectionSupport
import tech.ketc.anktfw.di.module.Module
import tech.ketc.anktfw.di.module.resolve
import java.net.HttpURLConnection.HTTP_OK
import java.util.concurrent.Executors
import kotlin.coroutines.experimental.coroutineContext

interface ImageService {
    suspend fun load(url: String): DeferredResponse<Bitmap?>
}

private val mImageLoadDispatcher: CoroutineDispatcher
        by lazy { Executors.newFixedThreadPool(2).asCoroutineDispatcher() }

private suspend inline fun mImageLoadContext() = coroutineContext + mImageLoadDispatcher

class ImageServiceImpl(override val module: Module = ServiceModule) :
        ImageService,
        InjectionSupport {

    private val urlConnectionFactory: HttpURLConnectionFactory by resolve()

    override suspend fun load(url: String) = asyncResponse(mImageLoadContext()) {
        val connection = urlConnectionFactory.create(url)
        return@asyncResponse try {
            connection.connect()
            connection.responseCode
                    .takeIf { it == HTTP_OK }
                    ?: return@asyncResponse null
            connection.inputStream.use {
                BitmapFactory.decodeStream(it)
            }
        } finally {
            connection.disconnect()
        }
    }
}
