package tech.ketc.anktfw.androidarch

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import tech.ketc.anktfw.R

import kotlinx.android.synthetic.main.activity_async_sample.*
import kotlinx.coroutines.experimental.CommonPool
import tech.ketc.anktfw.androidarch.croutine.asyncResponse
import tech.ketc.anktfw.androidarch.lifecycle.IOnActiveRunner
import tech.ketc.anktfw.androidarch.lifecycle.OnActiveRunner
import tech.ketc.anktfw.androidarch.lifecycle.bindLaunch

class AsyncSampleActivity : AppCompatActivity(), IOnActiveRunner by OnActiveRunner() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setLifecycleOwner
        setOwner(this)
        setContentView(R.layout.activity_async_sample)
        setSupportActionBar(toolbar)

        fab.setOnClickListener(::showSnackbar)
    }

    private fun showSnackbar(v: View) {
        //cancel job when onDestroy()
        bindLaunch {
            //after 5sec return Unit
            asyncResponse(coroutineContext + CommonPool) { Thread.sleep(5000L) }.await()
            //run when activity is active
            runOnActive { Snackbar.make(v, "snackbar", Snackbar.LENGTH_LONG).show() }
        }
    }
}
