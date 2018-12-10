package io.github.ketcktsd.anktfw.di

import io.github.ketcktsd.anktfw.di.container.*
import io.github.ketcktsd.anktfw.di.module.*


val DISampleActivity.module by lazy {
    module {
        singleton<ImageService> { ImageServiceImpl() }
    }
}

inline fun <reified T : Any> DISampleActivity.resolve(): Container<T> = module.resolve()
