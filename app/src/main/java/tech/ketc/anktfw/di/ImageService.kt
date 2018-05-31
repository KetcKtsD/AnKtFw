package tech.ketc.anktfw.di

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.asCoroutineDispatcher
import tech.ketc.anktfw.androidarch.croutine.DeferredResponse
import tech.ketc.anktfw.androidarch.croutine.asyncResponse
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import java.util.concurrent.Executors
import kotlin.coroutines.experimental.CoroutineContext

interface ImageService {
    fun load(url: String, context: CoroutineContext): DeferredResponse<Bitmap?>
}

private val mImageLoadDispatcher: CoroutineDispatcher
        by lazy { Executors.newFixedThreadPool(2).asCoroutineDispatcher() }

class ImageServiveImpl() : ImageService {
    override fun load(url: String, context: CoroutineContext) = asyncResponse(mImageLoadDispatcher + context) {
        val connection = (URL(url).openConnection() as HttpURLConnection).apply {
            allowUserInteraction = false
            requestMethod = "GET"
            instanceFollowRedirects = false
        }
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
