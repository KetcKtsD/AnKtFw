package io.github.ketcktsd.anktfw.di

import io.github.ketcktsd.anktfw.di.module.module
import io.github.ketcktsd.anktfw.di.module.resolve
import io.github.ketcktsd.anktfw.di.module.singleton


val DISampleActivity.module by lazy {
    module {
        singleton<ImageService> { ImageServiceImpl() }
    }
}

inline fun <reified T : Any> DISampleActivity.resolve() = module.resolve<T>()
