package io.github.ketcktsd.anktfw.bind.container

abstract class BindableContainer<T> {
    companion object {
        private val DEFAULT_ON_CHANGE: (Any?) -> Unit = {}
    }

    internal var onChange: (T) -> Unit = DEFAULT_ON_CHANGE
    var value: T
        set(value) {
            set(value)
            onChange(value)
        }
        get() = get()

    protected abstract fun set(value: T)
    protected abstract fun get(): T
}
