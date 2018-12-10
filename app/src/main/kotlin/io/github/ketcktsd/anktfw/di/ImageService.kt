package io.github.ketcktsd.anktfw.di

import android.graphics.*
import io.github.ketcktsd.anktfw.di.container.*
import io.github.ketcktsd.anktfw.di.module.*
import java.net.HttpURLConnection.HTTP_OK

interface ImageService {
    suspend fun load(url: String): Bitmap?
}

class ImageServiceImpl(override val module: Module = serviceModule) :
        ImageService,
        InjectionSupport {

    private val urlConnectionFactory: HttpURLConnectionFactory by resolve()

    override suspend fun load(url: String): Bitmap? {
        val connection = urlConnectionFactory.create(url)
        return try {
            connection.connect()
            connection.responseCode
                    .takeIf { it == HTTP_OK }
                    ?: return null
            connection.inputStream.use(BitmapFactory::decodeStream)
        } finally {
            connection.disconnect()
        }
    }
}
