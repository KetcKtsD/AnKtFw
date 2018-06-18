package tech.ketc.anktfw

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView
import tech.ketc.anktfw.androidarch.AsyncSampleActivity
import tech.ketc.anktfw.anko.UI
import tech.ketc.anktfw.anko.bindView
import tech.ketc.anktfw.anko.component
import tech.ketc.anktfw.di.DISampleActivity
import tech.ketc.anktfw.util.drawerLayout


interface IMainUI : UI<MainActivity, DrawerLayout> {
    val textView: TextView
    val appbarComponent: IAppbarComponent
}

class MainUI : IMainUI {
    override lateinit var root: DrawerLayout
        private set

    private val textViewId = View.generateViewId()
    override val textView: TextView by bindView(textViewId)

    override val appbarComponent: IAppbarComponent = AppbarComponent()

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
        customView<NavigationView> {
            inflateMenu(R.menu.navigation_main)
            setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.di_sample -> startActivity<DISampleActivity>()
                    R.id.arch_sample -> startActivity<AsyncSampleActivity>()
                }
                false
            }
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
}