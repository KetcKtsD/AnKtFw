package tech.ketc.anktfw.di.module

interface InjectionSupport {
    val module: Module
}

inline fun <reified T : Any> InjectionSupport.resolve() = module.resolve(T::class)