package tech.ketc.anktfw.anko

import android.app.Activity
import android.view.View
import org.jetbrains.anko.AnkoComponent

interface UI<A : Activity, R : View> : Component<R>, AnkoComponent<A>