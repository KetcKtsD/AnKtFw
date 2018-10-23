package io.github.ketcktsd.anktfw.di

import io.github.ketcktsd.anktfw.di.module.Module
import io.github.ketcktsd.anktfw.di.module.singleton


val sampleModule = Module {
    singleton<ImageService> { ImageServiceImpl() }
}
