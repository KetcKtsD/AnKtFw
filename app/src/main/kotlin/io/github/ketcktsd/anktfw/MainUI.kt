package io.github.ketcktsd.anktfw

import android.annotation.*
import android.view.*
import android.widget.*
import androidx.drawerlayout.widget.*
import io.github.ketcktsd.anktfw.anko.*
import io.github.ketcktsd.anktfw.util.*
import org.jetbrains.anko.*


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

    private val mNavigationViewId = View.generateViewId()

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
            id = mNavigationViewId
            menuId = R.menu.navigation_main
        }
    }

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        drawerLayout {
            root = this

            component(mMainContent).lparams(matchParent, matchParent)

            component(mNavigationContent).lparams(dip(280), matchParent) {
                gravity = Gravity.START
            }
        }
    }

    override fun onNavigationItemSelected(func: (MenuItem) -> Boolean) {
        mNavigationContent.root.setNavigationItemSelectedListener(func)
    }
}
