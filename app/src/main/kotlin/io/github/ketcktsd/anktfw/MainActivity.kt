package io.github.ketcktsd.anktfw

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity
import io.github.ketcktsd.anktfw.androidarch.AsyncSampleActivity
import io.github.ketcktsd.anktfw.animation.AnimationSampleActivity
import io.github.ketcktsd.anktfw.di.DISampleActivity

class MainActivity : AppCompatActivity(),
        IMainUI by MainUI() {

    private val mDrawerToggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this, root, 0, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this)
        initializeUI()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    private fun initializeUI() {
        setSupportActionBar(appbarComponent.toolbar)
        requireNotNull(supportActionBar).setDisplayHomeAsUpEnabled(true)
        root.addDrawerListener(mDrawerToggle)
        mDrawerToggle.isDrawerIndicatorEnabled = true
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
