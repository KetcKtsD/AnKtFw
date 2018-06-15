package tech.ketc.anktfw.androidarch

import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.asCoroutineDispatcher
import kotlinx.coroutines.experimental.delay
import tech.ketc.anktfw.androidarch.croutine.Failure
import tech.ketc.anktfw.androidarch.croutine.Success
import tech.ketc.anktfw.androidarch.croutine.asyncResponse
import tech.ketc.anktfw.androidarch.lifecycle.IOnActiveRunner
import tech.ketc.anktfw.androidarch.lifecycle.OnActiveRunner
import java.util.*
import java.util.concurrent.Executors
import kotlin.coroutines.experimental.coroutineContext
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.setContentView
import tech.ketc.anktfw.androidarch.lifecycle.bindLaunch

class AsyncSampleActivity : AppCompatActivity(),
        IOnActiveRunner by OnActiveRunner(),
        IAsyncSampleUI by AsyncSampleUI() {

    //For example, define such a frequently used asynchronous CoroutineContext in an external file.
    //To be an external file
    private val mTimeCountDispatcher: CoroutineDispatcher
            by lazy { Executors.newFixedThreadPool(2).asCoroutineDispatcher() }

    private suspend inline fun timeCountContext() = mTimeCountDispatcher + coroutineContext
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

            val response = asyncResponse(timeCountContext()) {
                delay(5000L)
                randomThrow()
            }.await()

            val message = when (response) {
                is Success -> response.result
                is Failure -> requireNotNull(response.error.message)
            }
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
