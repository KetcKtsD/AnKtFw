package io.github.ketcktsd.anktfw.di.container

import kotlin.reflect.KProperty

interface Container<T : Any> {
    val value: T
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T : Any> Container<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value

private class LazyContainer<T : Any>(init: () -> T) : Container<T> {
    override val value by lazy(init)
}

private class DiligentContainer<T : Any>(init: () -> T) : Container<T> {
    override val value = synchronized(this, init)
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
