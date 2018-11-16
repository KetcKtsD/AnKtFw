package io.github.ketcktsd.anktfw.di.module

import io.github.ketcktsd.anktfw.di.container.Container

interface InjectionSupport {
    val module: Module
}

inline fun <reified T : Any> InjectionSupport.resolve(): Container<T> = module.resolve(T::class)
