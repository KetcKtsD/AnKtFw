package io.github.ketcktsd.anktfw.di.container

import kotlin.reflect.KProperty

interface Container<T : Any> {
    val value: T
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
}

private class LazyContainer<T : Any>(init: () -> T) : Container<T> {
    override val value by lazy(init)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = value
}

private class DiligentContainer<T : Any>(init: () -> T) : Container<T> {
    override val value = init()

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = value
}

enum class InjectionMode {
    LAZY, DILIGENT
}

internal fun <T : Any> container(
        init: () -> T,
        mode: InjectionMode
) = when (mode) {
    InjectionMode.LAZY -> LazyContainer(init)
    InjectionMode.DILIGENT -> DiligentContainer(init)
}
