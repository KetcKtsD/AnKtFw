package io.github.ketcktsd.anktfw.di.module

import io.github.ketcktsd.anktfw.di.container.Container
import io.github.ketcktsd.anktfw.di.module.DependencyContainer
import io.github.ketcktsd.anktfw.di.module.DependencyProvider
import kotlin.reflect.KClass


abstract class Module(dsl: DependencyProvider.() -> Unit) {
    private val dependencyContainer = DependencyContainer().apply(dsl)

    /**
     * Search and retrieve dependency.
     * @param T is the dependency to searched
     * @param clazz is the dependency to searched
     *
     * @return Property object with dependency
     * @throws IllegalArgumentException thrown when dependency can not be resolved
     */
    fun <T : Any> resolve(clazz: KClass<T>): Container<T> = dependencyContainer.get(clazz)
}

/**
 * Search and retrieve dependency.
 * @param C [Module] containing the dependency you want to resolve
 * @param T is the dependency to searched
 */
inline fun <C : Module, reified T : Any> C.resolve() = this.resolve(T::class)
