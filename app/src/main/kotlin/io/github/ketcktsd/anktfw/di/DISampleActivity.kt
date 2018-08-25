package io.github.ketcktsd.anktfw.di

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.setContentView
import io.github.ketcktsd.anktfw.androidarch.croutine.failureIf
import io.github.ketcktsd.anktfw.androidarch.croutine.successIf
import io.github.ketcktsd.anktfw.androidarch.lifecycle.bindLaunch


class DISampleActivity : AppCompatActivity(), IDISampleUI by DISampleUI() {
    private val imageService: ImageService by resolve()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this)
        setSupportActionBar(toolbar)
        bindLaunch {
            val url = "any image url"
            val response = imageService.load(url).await()
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
