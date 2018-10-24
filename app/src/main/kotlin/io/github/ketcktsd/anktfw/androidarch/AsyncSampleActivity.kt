package io.github.ketcktsd.anktfw.androidarch

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import io.github.ketcktsd.anktfw.androidarch.croutine.asyncResult
import io.github.ketcktsd.anktfw.androidarch.lifecycle.IOnActiveRunner
import io.github.ketcktsd.anktfw.androidarch.lifecycle.OnActiveRunner
import io.github.ketcktsd.anktfw.androidarch.lifecycle.bindLaunch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import org.jetbrains.anko.setContentView
import java.util.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class AsyncSampleActivity : AppCompatActivity(),
        IOnActiveRunner by OnActiveRunner(),
        IAsyncSampleUI by AsyncSampleUI() {

    //For example, define such a frequently used asynchronous CoroutineContext in an external file.
    //To be an external file
    private val mTimeCountDispatcher: CoroutineDispatcher
            by lazy { Executors.newFixedThreadPool(2).asCoroutineDispatcher() }

    private val CoroutineScope.mTimeCountContext: CoroutineContext
        get() = coroutineContext + mTimeCountDispatcher
    //like the above


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this)
        //setLifecycleOwner
        setOwner(this)
        setSupportActionBar(toolbar)

        button.setOnClickListener(::showSnackbar)
    }

    private fun showSnackbar(v: View) {
        Toast.makeText(this, "onClick", Toast.LENGTH_LONG).show()
        //cancel job when onDestroy()
        bindLaunch {
            val result = asyncResult(mTimeCountContext) {
                delay(5000L)
                randomThrow()
            }.await()

            val message = result.fold({ it }, { requireNotNull(it.message) })
            //run when activity is active
            runOnActive { Snackbar.make(v, message, Snackbar.LENGTH_LONG).show() }
        }
    }

    private fun randomThrow(): String {
        val randomValue = Random().nextInt(100)
        return if (randomValue < 49) "success $randomValue"
        else throw Exception("failure $ $randomValue")
    }
}
