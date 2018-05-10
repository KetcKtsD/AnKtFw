package tech.ketc.anktfw.androidarch.lifecycle

import android.arch.lifecycle.*
import java.lang.ref.WeakReference

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

/**
 *
 */
class OnActiveRunner : IOnActiveRunner {

    private var mOwnerRef: WeakReference<LifecycleOwner>? = null
    private val mOwner by lazy { requireNotNull(mOwnerRef?.get()) }
    private val mObserver by lazy { createOnActiveLifeCycleObserver(mOwner) }
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

    private fun createOnActiveLifeCycleObserver(owner: LifecycleOwner) = object : LifecycleObserver {
        private val mOwnerRef = WeakReference(owner)
        private var isSafe = false
        private val tasks = ArrayList<() -> Unit>()

        fun run(task: () -> Unit) {
            if (isSafe) {
                task()
            } else {
                tasks.add(task)
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            isSafe = false
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            tasks.forEach { it() }
            tasks.clear()
            isSafe = true
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            isSafe = false
            val owner1 = mOwnerRef.get() ?: return
            owner1.lifecycle.removeObserver(this)
        }
    }
}