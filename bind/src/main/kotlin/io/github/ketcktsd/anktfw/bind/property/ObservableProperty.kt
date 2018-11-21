package io.github.ketcktsd.anktfw.bind.property

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

interface ObservableProperty<T> {
    var value: T
    fun addListener(listener: (T) -> Unit)
    fun removeListener(listener: (T) -> Unit)
    fun clearListener(listener: (T) -> Unit)
}

private class ObservablePropertyImpl<T>(
        private val set: (value: T) -> Unit,
        private val get: () -> T
) : ObservableProperty<T> {

    private var mIsUpdating = false
    private val mLock = ReentrantLock()

    override var value: T
        set(value) = mLock.withLock {
            if (mIsUpdating) return
            mIsUpdating = true
            set(value)
            mListeners.forEach { it(value) }
            mIsUpdating = false
        }
        get() = get()

    private val mListeners: MutableList<(T) -> Unit> = ArrayList()

    override fun addListener(listener: (T) -> Unit) {
        mListeners.add(listener)
    }

    override fun removeListener(listener: (T) -> Unit) {
        mListeners.remove(listener)
    }

    override fun clearListener(listener: (T) -> Unit) = mListeners.clear()
}

fun <T> observable(set: (value: T) -> Unit, get: () -> T): ObservableProperty<T> = ObservablePropertyImpl(set, get)
