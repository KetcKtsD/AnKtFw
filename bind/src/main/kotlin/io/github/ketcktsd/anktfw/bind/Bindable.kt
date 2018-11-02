package io.github.ketcktsd.anktfw.bind

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

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun setInternal(value: T) = set(value)
}

private class BindableImpl<T>(private val set: (value: T) -> Unit,
                              private val get: () -> T) : Bindable<T>() {

    override fun set(value: T) = this.set.invoke(value)

    override fun get(): T = this.get.invoke()
}

fun <T> bindable(set: (value: T) -> Unit, get: () -> T): Bindable<T> = BindableImpl(set, get)
