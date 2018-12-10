package io.github.ketcktsd.anktfw

import android.content.res.*
import android.os.*
import android.view.*
import androidx.appcompat.app.*
import io.github.ketcktsd.anktfw.androidarch.*
import io.github.ketcktsd.anktfw.animation.*
import io.github.ketcktsd.anktfw.bind.*
import io.github.ketcktsd.anktfw.di.*
import org.jetbrains.anko.*

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
            R.id.bind_sample -> startActivity<BindSampleActivity>()
        }
        return false
    }
}
