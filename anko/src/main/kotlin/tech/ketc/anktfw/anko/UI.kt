package tech.ketc.anktfw.anko

import android.app.*
import android.view.*
import org.jetbrains.anko.*

/**
 * interface for defining activity of ui
 *
 * @param A activity
 * @param R root view
 */
@Suppress("unused")
interface UI<A : Activity, R : View> : Component<R>, AnkoComponent<A> {
    override fun createView(ui: AnkoContext<A>): R
}
