package io.github.ketcktsd.anktfw.bind.collective

import io.github.ketcktsd.anktfw.bind.property.*
import kotlin.reflect.*


interface CollectiveBind<T> {
    var value: T
}

private class CollectiveBindImpl<T>(private val delegate: CollectiveBindDelegate<T>) : CollectiveBind<T> {
    override var value: T
        get() = delegate.get()
        set(value) = delegate.set(value)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> CollectiveBind<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> CollectiveBind<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    this.value = value
}

fun <T> bindCollective(initialValue: T,
                       vararg observables: ObservableProperty<T>
): CollectiveBind<T> = CollectiveBindImpl(CollectiveBindDelegateImpl(initialValue, arrayOf(*observables)))
