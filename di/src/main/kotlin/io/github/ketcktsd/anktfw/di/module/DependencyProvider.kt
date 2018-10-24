package io.github.ketcktsd.anktfw.di.module

import io.github.ketcktsd.anktfw.di.container.*
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.reflect.KClass

interface DependencyProvider {

    /**
     * register dependency with the same lifetime as the lifetime of the [Module].
     * also initialized when used for the first injection target.
     *
     * @param T Class of dependency
     * @param clazz Class of dependency
     * @param init Generate dependency
     * @throws IllegalArgumentException thrown when adding an already registered dependency
     */
    fun <T : Any> lazySingleton(clazz: KClass<T>, init: () -> T)

    /**
     * register dependency with the same lifetime as the lifetime of the module.
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
     * also initialized when used for the first injection target.
     *
     * @param T Class of dependency
     * @param clazz Class of dependency
     * @param init Generate dependency
     * @throws IllegalArgumentException thrown when adding an already registered dependency
     */
    fun <T : Any> lazyEach(clazz: KClass<T>, init: () -> T)

    /**
     * register dependency with the same lifetime as the lifetime of injection target.
     * also initialized at injection.
     *
     * @param T Class of dependency
     * @param clazz Class of dependency
     * @param init Generate dependency
     * @throws IllegalArgumentException thrown when adding an already registered dependency
     */
    fun <T : Any> each(clazz: KClass<T>, init: () -> T)
}

internal class DependencyContainer : DependencyProvider {
    companion object {
        private const val INITIAL_CAPACITY = 16
        private const val LOAD_FACTOR = 0.75f
        private const val ACCESS_ORDER = true
        private fun <K, V> lruMap() =
                LinkedHashMap<K, V>(INITIAL_CAPACITY, LOAD_FACTOR, ACCESS_ORDER)
    }

    private val map: MutableMap<KClass<*>, ContainerFactory<*>> = lruMap()
    private val lock = ReentrantLock()

    override fun <T : Any> lazySingleton(clazz: KClass<T>, init: () -> T) = clazz.run {
        put { SingletonContainerFactory(init, InjectionMode.LAZY) }
    }

    override fun <T : Any> singleton(clazz: KClass<T>, init: () -> T) = clazz.run {
        put { SingletonContainerFactory(init, InjectionMode.DILIGENT) }
    }

    override fun <T : Any> lazyEach(clazz: KClass<T>, init: () -> T) = clazz.run {
        put { EachContainerFactory(init, InjectionMode.LAZY) }
    }

    override fun <T : Any> each(clazz: KClass<T>, init: () -> T) = clazz.run {
        put { EachContainerFactory(init, InjectionMode.DILIGENT) }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(clazz: KClass<T>): Container<T> {
        val factory = map[clazz]
                ?: throw IllegalArgumentException("Dependency[${clazz.simpleName}] not added")
        return (factory as ContainerFactory<T>).get()
    }

    private inline fun <T : Any> KClass<T>.put(factory: () -> ContainerFactory<T>) = lock.withLock {
        if (map.containsKey(this))
            throw throw IllegalArgumentException("Added dependent classes [${this.simpleName}]")
        map[this] = factory()
    }
}
