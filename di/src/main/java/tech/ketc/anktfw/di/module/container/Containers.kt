package tech.ketc.anktfw.di.module.container

import kotlin.reflect.KProperty

interface Container<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
}

class EachContainer<T>(private val initializer: () -> T) : Container<T> {

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return initializer()
    }
}

class SingletonContainer<T>(private val initializer: () -> T) : Container<T> {
    private val value by lazy(initializer)
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = value
}