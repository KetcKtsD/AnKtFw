package tech.ketc.anktfw.di.module

import tech.ketc.anktfw.di.module.container.Container
import tech.ketc.anktfw.di.module.container.EachContainer
import tech.ketc.anktfw.di.module.container.SingletonContainer
import kotlin.reflect.KClass

interface DependencyDsl {
    infix fun <T : Any> KClass<T>.resolutionOf(container: Container<T>)
}

class DependencyContainer : DependencyDsl {

    private val dependencyMap = LinkedHashMap<KClass<*>, Container<*>>()

    override infix fun <T : Any> KClass<T>.resolutionOf(container: Container<T>) {
        val c = dependencyMap[this]
        if (c == null) {
            dependencyMap[this] = container
        } else {
            throw IllegalArgumentException("Added dependent classes [${this.simpleName}]")
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(clazz: KClass<T>): Container<T> {
        return dependencyMap[clazz] as Container<T>
    }
}

fun <T> DependencyDsl.singleton(initializer: () -> T) = SingletonContainer(initializer)

fun <T> DependencyDsl.each(initializer: () -> T) = EachContainer(initializer)