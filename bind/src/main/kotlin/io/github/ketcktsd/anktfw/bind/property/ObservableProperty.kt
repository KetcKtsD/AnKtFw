package io.github.ketcktsd.anktfw.bind.property

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

    override var value: T
        set(value) {
            if (get() == value) return
            set(value)
            mListeners.forEach { it(value) }
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
