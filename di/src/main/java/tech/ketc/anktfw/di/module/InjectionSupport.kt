package tech.ketc.anktfw.di.module

interface InjectSupport {
    val module: Module
}

inline fun <reified T : Any> InjectSupport.resolve() = module.resolve(T::class)