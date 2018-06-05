package tech.ketc.anktfw.di

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import tech.ketc.anktfw.androidarch.croutine.failureIf
import tech.ketc.anktfw.androidarch.croutine.successIf
import tech.ketc.anktfw.androidarch.lifecycle.bindLaunch

class DISampleActivity : AppCompatActivity(), IDISampleUI by DISampleUI() {
    private val imageService: ImageService by resolve()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        setSupportActionBar(toolbar)
        bindLaunch {
            val url = "any image url"
            val response = imageService.load(coroutineContext, url).await()
            fun ImageView.setImage(bitmap: Bitmap?) {
                setImageBitmap(bitmap)
                this.animate().apply {
                    duration = 300
                    alphaBy(0f)
                    alpha(1f)
                }
            }
            response.successIf(image::setImage)
            response.failureIf(Throwable::printStackTrace)
        }
    }
}