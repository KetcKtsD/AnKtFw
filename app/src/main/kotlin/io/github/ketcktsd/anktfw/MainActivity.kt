package io.github.ketcktsd.anktfw

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity
import io.github.ketcktsd.anktfw.androidarch.AsyncSampleActivity
import io.github.ketcktsd.anktfw.animation.AnimationSampleActivity
import io.github.ketcktsd.anktfw.di.DISampleActivity

class MainActivity : AppCompatActivity(), IMainUI by MainUI() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this)
        initializeUI()
    }

    private fun initializeUI() {
        setSupportActionBar(appbarComponent.toolbar)
        onNavigationItemSelected(::onNavigationItemSelected)
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.start_animation_sample -> startActivity<AnimationSampleActivity>()
            R.id.start_arch_sample -> startActivity<AsyncSampleActivity>()
            R.id.start_di_sample -> startActivity<DISampleActivity>()
        }
        return false
    }
}
