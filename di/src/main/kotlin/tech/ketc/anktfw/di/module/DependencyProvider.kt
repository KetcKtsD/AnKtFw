package tech.ketc.anktfw.di.module

import tech.ketc.anktfw.di.container.Container
import tech.ketc.anktfw.di.container.LazyContainer
import tech.ketc.anktfw.di.container.SimpleContainer
import kotlin.reflect.KClass

interface DependencyProvider {

    /**
     * Register dependency with the same lifetime as the lifetime of the module.
     * Also initialized when used for the first injection target.
     *
     * @param T Class of dependency
     * @param initializer Generate dependency
     *
     * @throws IllegalArgumentException thrown when adding an already registered dependency
     */
    infix fun <T : Any> KClass<T>.lazySingleton(initializer: () -> T)

    /**
     * Register dependency with the same lifetime as the lifetime of the module.
     * Dependency is generated when initializing the [Module].
     * @param T Class of dependency
     * @param initializer Generate dependency
     */
    infix fun <T : Any> KClass<T>.singleton(initializer: () -> T)

    /**
     * Register dependency with the same lifetime as the lifetime of injection target.
     * Also initialized when used for the first injection target.
     *
     * @param T Class of dependency
     * @param initializer Generate dependency
     */
    infix fun <T : Any> KClass<T>.lazyEach(initializer: () -> T)

    /**
     * Register dependency with the same lifetime as the lifetime of injection target.
     * Also initialized at injection.
     *
     * @param T Class of dependency
     * @param initializer Generate dependency
     */
    infix fun <T : Any> KClass<T>.each(initializer: () -> T)
}

private typealias DP = DependencyProvider

inline fun <reified T : Any> DP.lazySingleton(noinline initialize: () -> T) = T::class lazySingleton initialize
inline fun <reified T : Any> DP.singleton(noinline initialize: () -> T) = T::class singleton initialize
inline fun <reified T : Any> DP.lazyEach(noinline initialize: () -> T) = T::class lazyEach initialize
inline fun <reified T : Any> DP.each(noinline initialize: () -> T) = T::class each initialize


internal class DependencyContainer : DependencyProvider {

    companion object {
        private const val INITIAL_CAPACITY = 16
        private const val LOAD_FACTOR = 0.75f
        private const val ACCESS_ORDER = true
        private fun <K, V> lruMap() = LinkedHashMap<K, V>(INITIAL_CAPACITY, LOAD_FACTOR, ACCESS_ORDER)
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

    override fun <T : Any> KClass<T>.each(initializer: () -> T) {
        internalEach { SimpleContainer(initializer) }
    }

    override fun <T : Any> KClass<T>.lazyEach(initializer: () -> T) {
        internalEach { LazyContainer(initializer) }
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
        val existEach = eachMap.containsKey(clazz)
        val existSingleton = singletonMap.containsKey(clazz)
        if (existEach || existSingleton)
            throw throw IllegalArgumentException("Added dependent classes [${clazz.simpleName}]")
    }
}