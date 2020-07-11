package tech.ketc.anktfw.arch.croutine

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import java.util.concurrent.locks.*
import kotlin.concurrent.*
import kotlin.coroutines.*

interface LifecycleScope : CoroutineScope {
    fun <T> channel(): Channel<T>
    fun <T> channel(capacity: Int): Channel<T>
}

private class LifecycleScopeImpl(
        private val lifecycleOwner: LifecycleOwner,
        coroutineScope: CoroutineScope
) : LifecycleScope, LifecycleObserver {

    init {
        coroutineScope.coroutineContext[Job]
                ?.let { throw IllegalArgumentException("A Context with Job already passed") }

        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val mJob = SupervisorJob()
    private val channels: MutableList<Channel<*>> = ArrayList()
    private val rwLock = ReentrantReadWriteLock()

    override val coroutineContext: CoroutineContext = (coroutineScope + mJob).coroutineContext

    override fun <T> channel(): Channel<T> = rwLock.write {
        Channel<T>().also { channels.add(it) }
    }

    override fun <T> channel(capacity: Int): Channel<T> = rwLock.write {
        Channel<T>(capacity).also { channels.add(it) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        cleanChannels()
        mJob.cancelChildren()
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    private fun cleanChannels() = rwLock.read {
        channels.forEach {
            it.cancel()
            it.close()
        }
    }
}

@Suppress("FunctionName")
fun LifecycleScope(
        lifecycleOwner: LifecycleOwner,
        coroutineScope: CoroutineScope = AndroidScope
): LifecycleScope = LifecycleScopeImpl(lifecycleOwner, coroutineScope)

@Suppress("NOTHING_TO_INLINE")
inline fun LifecycleScope.bindLaunch(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        noinline block: suspend CoroutineScope.() -> Unit
) = launch(coroutineContext, start, block)
