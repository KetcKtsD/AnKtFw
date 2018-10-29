package io.github.ketcktsd.anktfw.bind.container

abstract class Bindable<T> {
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

    internal fun setInternal(value: T) = set(value)
}

fun <T> bindable(set: (value: T) -> Unit, get: () -> T) = object : Bindable<T>() {

    override fun set(value: T) = set(value)

    override fun get(): T = get()
}
