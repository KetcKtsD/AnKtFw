package tech.ketc.anktfw.di.container

import kotlin.reflect.KProperty

interface Container<T : Any> {
    val value: T
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
}

internal class LazyContainer<T : Any>(initializer: () -> T) : Container<T> {
    override val value by lazy(initializer)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = value
}

internal class SimpleContainer<T : Any>(initializer: () -> T) : Container<T> {
    override val value = initializer()

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = value
}