package tech.ketc.anktfw

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import org.jetbrains.anko.*
import tech.ketc.anktfw.anko.UI
import tech.ketc.anktfw.anko.bindView
import tech.ketc.anktfw.anko.component
import tech.ketc.anktfw.util.drawerLayout
import tech.ketc.anktfw.util.menuId
import tech.ketc.anktfw.util.navigationView


interface IMainUI : UI<MainActivity, DrawerLayout> {
    val textView: TextView
    val appbarComponent: AppbarComponent

    fun onNavigationItemSelected(func: (MenuItem) -> Boolean)
}

class MainUI : IMainUI {

    override lateinit var root: DrawerLayout
        private set

    private val mTextViewId = View.generateViewId()
    override val textView: TextView by bindView(mTextViewId)

    override val appbarComponent: AppbarComponent = SimpleAppbarComponent()

    @SuppressLint("SetTextI18n")
    private val mMainContent = component {
        relativeLayout {
            component(appbarComponent) {
                id = View.generateViewId()
            }.lparams(matchParent, wrapContent)

            textView {
                id = mTextViewId
                text = "Hello World!!"
            }.lparams(wrapContent, wrapContent) {
                centerInParent()
            }
        }
    }

    private val mNavigationContent = component {
        navigationView {
            menuId = R.menu.navigation_main
        }
    }

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        drawerLayout {
            root = this

            component(mMainContent).lparams(matchParent, matchParent)

            component(mNavigationContent).lparams(matchParent, matchParent) {
                gravity = Gravity.START
            }
        }
    }

    override fun onNavigationItemSelected(func: (MenuItem) -> Boolean) {
        mNavigationContent.root.setNavigationItemSelectedListener(func)
    }
}
