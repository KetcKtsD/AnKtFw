package io.github.ketcktsd.anktfw.di

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.ketcktsd.anktfw.androidarch.croutine.asyncResult
import io.github.ketcktsd.anktfw.androidarch.lifecycle.bindLaunch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import org.jetbrains.anko.setContentView
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext


class DISampleActivity : AppCompatActivity(), IDISampleUI by DISampleUI() {
    companion object {
        private const val UNTIL_DOWNLOAD_START_MILLS = 1000L
    }

    private val imageService: ImageService by resolve()

    private val mImageLoadDispatcher: CoroutineDispatcher
            by lazy { Executors.newFixedThreadPool(2).asCoroutineDispatcher() }

    private val CoroutineScope.mImageLoadContext: CoroutineContext
        get() = coroutineContext + mImageLoadDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this)
        setSupportActionBar(toolbar)
        bindLaunch {
            delay(UNTIL_DOWNLOAD_START_MILLS)
            val url = "https://pbs.twimg.com/profile_banners/408464571/1398618018/1500x500"
            val result = asyncResult(mImageLoadContext) { imageService.load(url) }.await()
            result.fold(imageView::setImageBitmap, Throwable::printStackTrace)
        }
    }
}
