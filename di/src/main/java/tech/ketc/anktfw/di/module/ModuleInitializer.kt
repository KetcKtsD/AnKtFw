package tech.ketc.anktfw.di.module

import tech.ketc.anktfw.di.module.container.Container
import tech.ketc.anktfw.di.module.container.LazyContainer
import tech.ketc.anktfw.di.module.container.SimpleContainer
import kotlin.reflect.KClass

interface ModuleInitializer {

    infix fun <T : Any> KClass<T>.lazySingleton(initializer: () -> T)
    infix fun <T : Any> KClass<T>.singleton(initializer: () -> T)

    infix fun <T : Any> KClass<T>.lazyEach(initializer: () -> T)
    infix fun <T : Any> KClass<T>.each(initializer: () -> T)
}

private typealias MI = ModuleInitializer

inline fun <reified T : Any> MI.lazySingleton(noinline initialize: () -> T) = T::class lazySingleton initialize
inline fun <reified T : Any> MI.singleton(noinline initialize: () -> T) = T::class singleton initialize
inline fun <reified T : Any> MI.lazyEach(noinline initialize: () -> T) = T::class lazyEach initialize
inline fun <reified T : Any> MI.each(noinline initialize: () -> T) = T::class each initialize


internal class DependencyContainer : ModuleInitializer {

    companion object {
        private const val INITIAL_CAPACITY = 16
        private const val LOAD_FACTOR = 0.75f
        private const val ACCESS_ORDER = true
    }

    private val singletonMap = LinkedHashMap<KClass<*>, Container<*>>(INITIAL_CAPACITY, LOAD_FACTOR, ACCESS_ORDER)

    private val eachMap = LinkedHashMap<KClass<*>, () -> Container<*>>(INITIAL_CAPACITY, LOAD_FACTOR, ACCESS_ORDER)

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

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(clazz: KClass<T>): Container<T> {
        return singletonMap[clazz] as? Container<T>
                ?: eachMap[clazz]?.invoke() as? Container<T>
                ?: throw IllegalArgumentException("Dependency[${clazz.simpleName}] not added")
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