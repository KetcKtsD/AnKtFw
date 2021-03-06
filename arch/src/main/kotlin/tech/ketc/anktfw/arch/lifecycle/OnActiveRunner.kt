package tech.ketc.anktfw.arch.lifecycle

import androidx.lifecycle.*
import java.util.*

/**
 * A interface that run arbitrary function when [LifecycleOwner] is Active
 */
interface OnActiveRunner {

    /**
     * @param handle If the [handle] is called after [Lifecycle.Event.ON_PAUSE], the [handle] is called on [Lifecycle.Event.ON_RESUME] is called.
     */
    fun runOnActive(handle: () -> Unit)
}

@Suppress("FunctionName")
fun OnActiveRunner(owner: LifecycleOwner): OnActiveRunner = OnActiveRunnerImpl(owner)

private class OnActiveRunnerImpl(
        private val owner: LifecycleOwner
) : OnActiveRunner, LifecycleObserver {
    private var mIsSafe = false
    private var mIsDestroy = false
    private val mTasks = LinkedList<() -> Unit>()

    init {
        owner.lifecycle.addObserver(this)
    }

    override fun runOnActive(handle: () -> Unit) {
        if (mIsDestroy) return
        if (mIsSafe) handle()
        else mTasks.addLast(handle)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() = runAllTasks()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        mIsSafe = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() = runAllTasks()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mIsDestroy = true
        mTasks.clear()
        owner.lifecycle.removeObserver(this)
    }

    private fun runAllTasks() {
        mTasks.forEach { it() }
        mTasks.clear()
        mIsSafe = true
    }
}
