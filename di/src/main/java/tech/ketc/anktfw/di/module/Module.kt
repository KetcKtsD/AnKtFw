package tech.ketc.anktfw.di.module

import tech.ketc.anktfw.di.module.container.Container
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


abstract class Module(dsl: ModuleInitializer.() -> Unit) {
    private val dependencyContainer = DependencyContainer().apply(dsl)

    fun <T : Any> resolve(clazz: KClass<T>): Container<T> = dependencyContainer.get(clazz)

    inline operator fun <reified T : Any> getValue(thisRef: Any?, property: KProperty<*>): T {
        return resolve(T::class).getValue(thisRef, property)
    }
}

inline fun <C : Module, reified T : Any> C.resolve() = this.resolve(T::class)