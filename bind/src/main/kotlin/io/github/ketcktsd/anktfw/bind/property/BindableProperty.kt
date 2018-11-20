package io.github.ketcktsd.anktfw.bind.property

/**
 *
 */
class BindableProperty<T>(internal val set: (value: T) -> Unit,
                          internal val get: () -> T) {
    companion object {
        private val DEFAULT_ON_CHANGE: (Any?) -> Unit = {}
    }

    internal var onChange: (BindableProperty<T>) -> Unit = DEFAULT_ON_CHANGE

    var value: T
        set(value) {
            set(value)
            onChange(this)
        }
        get() = get()
}

fun <T> bindable(set: (value: T) -> Unit, get: () -> T): BindableProperty<T> = BindableProperty(set, get)
