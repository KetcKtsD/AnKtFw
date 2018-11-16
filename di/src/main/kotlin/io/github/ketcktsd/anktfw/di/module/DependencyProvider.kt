package io.github.ketcktsd.anktfw.di.module

import io.github.ketcktsd.anktfw.di.container.*
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

/**
 * it provides an interface for registering dependencies.
 */
interface DependencyProvider {

    /**
     * register dependency with the same lifetime as the lifetime of the [Module].
     * dependencies are generated the first time they are used at the injection target.
     *
     * @param T Class of dependency
     * @param clazz Class of dependency
     * @param init Generate dependency
     * @throws IllegalArgumentException thrown when adding an already registered dependency
     */
    fun <T : Any> lazySingleton(clazz: KClass<T>, init: () -> T)

    /**
     * register the dependency of the same scope as injection target property.
     * dependency is generated when initializing the [Module].
     *
     * @param T Class of dependency
     * @param clazz Class of dependency
     * @param init Generate dependency
     * @throws IllegalArgumentException thrown when adding an already registered dependency
     */
    fun <T : Any> singleton(clazz: KClass<T>, init: () -> T)

    /**
     * register dependency with the same lifetime as the lifetime of injection target.
     * dependency are generated when used for the first time at the injection target.
     *
     * @param T Class of dependency
     * @param clazz Class of dependency
     * @param init Generate dependency
     * @throws IllegalArgumentException thrown when adding an already registered dependency
     */
    fun <T : Any> lazyEach(clazz: KClass<T>, init: () -> T)

    /**
     * register the dependency of the same scope as injection target property.
     * dependency is initialized at injection.
     *
     * @param T Class of dependency
     * @param clazz Class of dependency
     * @param init Generate dependency
     * @throws IllegalArgumentException thrown when adding an already registered dependency
     */
    fun <T : Any> each(clazz: KClass<T>, init: () -> T)
}

internal class DependencyProviderInternal : DependencyProvider {
    companion object {
        private const val INITIAL_CAPACITY = 16
        private const val LOAD_FACTOR = 0.75f
        private const val ACCESS_ORDER = true
        private fun <K, V> lruMap() =
                LinkedHashMap<K, V>(INITIAL_CAPACITY, LOAD_FACTOR, ACCESS_ORDER)
    }

    private val map: MutableMap<String, ContainerFactory<*>> = lruMap()
    private val lock = ReentrantLock()

    override fun <T : Any> lazySingleton(clazz: KClass<T>, init: () -> T) = put(clazz) {
        SingletonContainerFactory(init, InjectionMode.LAZY)
    }

    override fun <T : Any> singleton(clazz: KClass<T>, init: () -> T) = put(clazz) {
        SingletonContainerFactory(init, InjectionMode.DILIGENT)
    }

    override fun <T : Any> lazyEach(clazz: KClass<T>, init: () -> T) = put(clazz) {
        EachContainerFactory(init, InjectionMode.LAZY)
    }

    override fun <T : Any> each(clazz: KClass<T>, init: () -> T) = put(clazz) {
        EachContainerFactory(init, InjectionMode.DILIGENT)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(clazz: KClass<T>): Container<T> {
        val factory = map[clazz.jvmName]
                ?: throw IllegalArgumentException("Dependency[${clazz.simpleName}] not added")
        return (factory as ContainerFactory<T>).get()
    }

    private inline fun <T : Any> put(clazz: KClass<T>, factory: () -> ContainerFactory<T>) = lock.withLock {
        val name = clazz.jvmName
        if (map.containsKey(name))
            throw throw IllegalArgumentException("Added dependent classes [${clazz.simpleName}]")
        map[name] = factory()
    }
}
