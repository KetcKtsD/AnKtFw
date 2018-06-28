package tech.ketc.anktfw

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import org.jetbrains.anko.*
import tech.ketc.anktfw.androidarch.AsyncSampleActivity
import tech.ketc.anktfw.animation.AnimationSampleActivity
import tech.ketc.anktfw.anko.UI
import tech.ketc.anktfw.anko.bindView
import tech.ketc.anktfw.anko.component
import tech.ketc.anktfw.di.DISampleActivity
import tech.ketc.anktfw.util.drawerLayout
import tech.ketc.anktfw.util.menuId
import tech.ketc.anktfw.util.navigationView


interface IMainUI : UI<MainActivity, DrawerLayout> {
    val textView: TextView
    val appbarComponent: AppbarComponent
}

class MainUI : IMainUI {
    override lateinit var root: DrawerLayout
        private set

    private val textViewId = View.generateViewId()
    override val textView: TextView by bindView(textViewId)

    override val appbarComponent: AppbarComponent = SimpleAppbarComponent()

    @SuppressLint("SetTextI18n")
    private val mainContent = component {
        relativeLayout {
            component(appbarComponent) {
                id = View.generateViewId()
            }.lparams(matchParent, wrapContent)

            textView {
                id = textViewId
                text = "Hello World!!"
            }.lparams(wrapContent, wrapContent) {
                centerInParent()
            }
        }
    }

    private val navigationContent = component {
        navigationView {
            menuId = R.menu.navigation_main
            setNavigationItemSelectedListener { onNavigationItemSelected(it) }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        drawerLayout {
            root = this

            component(mainContent).lparams(matchParent, matchParent)

            component(navigationContent).lparams(matchParent, matchParent) {
                gravity = Gravity.START
            }
        }
    }

    private fun Context.onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.di_sample -> startActivity<DISampleActivity>()
            R.id.arch_sample -> startActivity<AsyncSampleActivity>()
            R.id.animation_sample -> startActivity<AnimationSampleActivity>()
        }
        return false
    }
}
