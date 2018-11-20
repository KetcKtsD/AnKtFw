package io.github.ketcktsd.anktfw.bind.collective

import io.github.ketcktsd.anktfw.bind.property.BindableProperty
import kotlin.reflect.KProperty


interface BindableCollective<T> {
    var value: T
}

class BindableCollectiveImpl<T>(initialValue: T, bindables: Array<BindableProperty<T>>) : BindableCollective<T> {
    private val delegate = CollectiveBindDelegate(initialValue, bindables)
    override var value: T
        get() = delegate.get()
        set(value) = delegate.set(value)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> BindableCollective<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> BindableCollective<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    this.value = value
}

fun <T> bindableCollective(initialValue: T,
                           vararg bindables: BindableProperty<T>
): BindableCollective<T> = BindableCollectiveImpl(initialValue, arrayOf(*bindables))
