package io.github.ketcktsd.anktfw.di

import io.github.ketcktsd.anktfw.di.module.Module
import io.github.ketcktsd.anktfw.di.module.singleton


class SampleModule : Module({
    singleton<ImageService> { ImageServiceImpl() }
})
