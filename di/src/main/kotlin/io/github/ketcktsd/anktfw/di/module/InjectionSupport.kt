package io.github.ketcktsd.anktfw.di.module

import io.github.ketcktsd.anktfw.di.module.Module

interface InjectionSupport {
    val module: Module
}

inline fun <reified T : Any> InjectionSupport.resolve() = module.resolve(T::class)
