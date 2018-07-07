package tech.ketc.anktfw.di.module

import tech.ketc.anktfw.di.container.Container
import tech.ketc.anktfw.di.container.LazyContainer
import tech.ketc.anktfw.di.container.SimpleContainer
import java.util.*
import kotlin.reflect.KClass

interface DependencyProvider {

    /**
     * register dependency with the same lifetime as the lifetime of the [Module].
     * also initialized when used for the first injection target.
     *
     * @param T Class of dependency
     *
     * @param initializer Generate dependency
     *
     * @throws IllegalArgumentException thrown when adding an already registered dependency
     */
    infix fun <T : Any> KClass<T>.lazySingleton(initializer: () -> T)

    /**
     * register dependency with the same lifetime as the lifetime of the module.
     * dependency is generated when initializing the [Module].
     *
     * @param T Class of dependency
     *
     * @param initializer Generate dependency
     *
     * @throws IllegalArgumentException thrown when adding an already registered dependency
     */
    infix fun <T : Any> KClass<T>.singleton(initializer: () -> T)

    /**
     * register dependency with the same lifetime as the lifetime of injection target.
     * also initialized when used for the first injection target.
     *
     * @param T Class of dependency
     *
     * @param initializer Generate dependency
     *
     * @throws IllegalArgumentException thrown when adding an already registered dependency
     */
    infix fun <T : Any> KClass<T>.lazyEach(initializer: () -> T)

    /**
     * register dependency with the same lifetime as the lifetime of injection target.
     * also initialized at injection.
     *
     * @param T Class of dependency
     *
     * @param initializer Generate dependency
     *
     * @throws IllegalArgumentException thrown when adding an already registered dependency
     */
    infix fun <T : Any> KClass<T>.each(initializer: () -> T)
}

/**
 * @see [DependencyProvider.lazySingleton]
 */
inline fun <reified T : Any> DependencyProvider.lazySingleton(noinline initialize: () -> T) =
        T::class lazySingleton initialize

/**
 * @see [DependencyProvider.singleton]
 */
inline fun <reified T : Any> DependencyProvider.singleton(noinline initialize: () -> T) =
        T::class singleton initialize

/**
 * @see [DependencyProvider.lazyEach]
 */
inline fun <reified T : Any> DependencyProvider.lazyEach(noinline initialize: () -> T) =
        T::class lazyEach initialize

/**
 * @see [DependencyProvider.each]
 */
inline fun <reified T : Any> DependencyProvider.each(noinline initialize: () -> T) =
        T::class each initialize

internal class DependencyContainer : DependencyProvider {

    companion object {
        private const val INITIAL_CAPACITY = 16
        private const val LOAD_FACTOR = 0.75f
        private const val ACCESS_ORDER = true
        private fun <K, V> lruMap() =
                LinkedHashMap<K, V>(INITIAL_CAPACITY, LOAD_FACTOR, ACCESS_ORDER)
    }

    private val singletonMap: LinkedHashMap<KClass<*>, Container<*>> = lruMap()

    private val eachMap: LinkedHashMap<KClass<*>, () -> Container<*>> = lruMap()

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(clazz: KClass<T>): Container<T> {
        return singletonMap[clazz] as? Container<T>
                ?: eachMap[clazz]?.invoke() as? Container<T>
                ?: throw IllegalArgumentException("Dependency[${clazz.simpleName}] not added")
    }

    override infix fun <T : Any> KClass<T>.lazySingleton(initializer: () -> T) {
        internalSingleton { LazyContainer(initializer) }
    }

    override fun <T : Any> KClass<T>.singleton(initializer: () -> T) {
        internalSingleton { SimpleContainer(initializer) }
    }

    override fun <T : Any> KClass<T>.lazyEach(initializer: () -> T) {
        internalEach { LazyContainer(initializer) }
    }

    override fun <T : Any> KClass<T>.each(initializer: () -> T) {
        internalEach { SimpleContainer(initializer) }
    }

    private fun <T : Any> KClass<T>.internalSingleton(container: () -> Container<T>) {
        checkUniqueness(this)
        singletonMap[this] = container()
    }

    private fun <T : Any> KClass<T>.internalEach(container: () -> Container<T>) {
        checkUniqueness(this)
        eachMap[this] = container
    }

    private fun <T : Any> checkUniqueness(clazz: KClass<T>) {
        fun existSingleton() = singletonMap.containsKey(clazz)
        fun existEach() = eachMap.containsKey(clazz)
        if (existSingleton() || existEach())
            throw throw IllegalArgumentException("Added dependent classes [${clazz.simpleName}]")
    }
}
