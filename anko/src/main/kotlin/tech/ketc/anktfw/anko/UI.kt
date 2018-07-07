package tech.ketc.anktfw.anko

import android.app.Activity
import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

/**
 * interface for defining activity of ui
 *
 * @param A activity
 * @param R root view
 */
interface UI<A : Activity, R : View> : Component<R>, AnkoComponent<A> {
    override fun createView(ui: AnkoContext<A>): R
}