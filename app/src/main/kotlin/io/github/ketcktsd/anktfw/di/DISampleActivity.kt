package io.github.ketcktsd.anktfw.di

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.setContentView
import io.github.ketcktsd.anktfw.androidarch.croutine.failureIf
import io.github.ketcktsd.anktfw.androidarch.croutine.successIf
import io.github.ketcktsd.anktfw.androidarch.lifecycle.bindLaunch
import kotlinx.coroutines.delay


class DISampleActivity : AppCompatActivity(), IDISampleUI by DISampleUI() {
    companion object {
        private const val UNTIL_DOWNLOAD_START_MILLS = 1000L
    }

    private val imageService: ImageService by resolve()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this)
        setSupportActionBar(toolbar)
        bindLaunch {
            delay(UNTIL_DOWNLOAD_START_MILLS)
            val url = "https://pbs.twimg.com/profile_banners/408464571/1398618018/1500x500"
            val response = imageService.load(url).await()
            response.successIf(imageView::setImageBitmap)
            response.failureIf(Throwable::printStackTrace)
        }
    }
}
