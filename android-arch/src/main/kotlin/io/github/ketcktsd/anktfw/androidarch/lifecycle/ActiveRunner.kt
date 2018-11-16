package io.github.ketcktsd.anktfw.androidarch.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.lang.ref.WeakReference
import kotlin.properties.Delegates

/**
 * A interface that run arbitrary functions when [LifecycleOwner] is Active
 */
interface IOnActiveRunner {
    /**
     * Set at the beginning of the life cycle
     * @param owner owner
     * @throws IllegalStateException When already set
     */
    fun setOwner(owner: LifecycleOwner)

    /**
     * @param handle If the [handle] is called after [Lifecycle.Event.ON_PAUSE], the [handle] is called when [Lifecycle.Event.ON_RESUME] is called.
     * @throws IllegalStateException When owner is not set
     */
    fun runOnActive(handle: () -> Unit)
}

class OnActiveRunner : IOnActiveRunner {

    private var mOwnerRef: WeakReference<LifecycleOwner> by Delegates.notNull()
    private val mOwner by lazy { requireNotNull(mOwnerRef.get()) }
    private val mObserver by lazy { OnActiveLifeCycleObserver(mOwner) }
    private var mIsOwnerInitialized = false

    override fun setOwner(owner: LifecycleOwner) {
        if (mIsOwnerInitialized) throw IllegalStateException("owner already set")
        mOwnerRef = WeakReference(owner)
        owner.lifecycle.addObserver(mObserver)
        mIsOwnerInitialized = true
    }

    override fun runOnActive(handle: () -> Unit) {
        if (!mIsOwnerInitialized) throw IllegalStateException("owner is not set")
        mObserver.run(handle)
    }
}

private class OnActiveLifeCycleObserver(owner: LifecycleOwner) : LifecycleObserver {
    private val mOwnerRef = WeakReference(owner)
    private var mIsSafe = false
    private val mTasks = ArrayList<() -> Unit>()

    fun run(task: () -> Unit) {
        if (mIsSafe) {
            task()
        } else {
            mTasks.add(task)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        mIsSafe = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        mTasks.forEach { it() }
        mTasks.clear()
        mIsSafe = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mIsSafe = false
        val owner = mOwnerRef.get() ?: return
        owner.lifecycle.removeObserver(this)
    }
}
