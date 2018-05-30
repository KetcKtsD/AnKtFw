package tech.ketc.anktfw.di.module.container

import kotlin.reflect.KProperty

interface Container<T : Any> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
}

internal class LazyContainer<T : Any>(initializer: () -> T) : Container<T> {
    private val value by lazy(initializer)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = value
}

internal class SimpleContainer<T : Any>(initializer: () -> T) : Container<T> {
    private val value = initializer()

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = value
}