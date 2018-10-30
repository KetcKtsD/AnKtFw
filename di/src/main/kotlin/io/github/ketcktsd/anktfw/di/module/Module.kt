package io.github.ketcktsd.anktfw.di.module

import io.github.ketcktsd.anktfw.di.container.Container
import kotlin.reflect.KClass

class Module(dsl: DependencyProvider.() -> Unit) {
    private val provider = DependencyProviderInternal().apply(dsl)

    /**
     * Search and retrieve dependency.
     * @param T is the dependency to searched
     * @param clazz is the dependency to searched
     *
     * @return Property object with dependency
     * @throws IllegalArgumentException thrown when dependency can not be resolved
     */
    fun <T : Any> resolve(clazz: KClass<T>): Container<T> = provider.get(clazz)
}

/**
 * Search and retrieve dependency.
 * @param T is the dependency to searched
 */
inline fun <reified T : Any> Module.resolve() = this.resolve(T::class)

@Suppress("NOTHING_TO_INLINE")
inline fun module(noinline dsl: DependencyProvider.() -> Unit) = Module(dsl)
