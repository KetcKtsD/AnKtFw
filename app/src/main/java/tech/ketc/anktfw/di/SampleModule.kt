package tech.ketc.anktfw.di

import tech.ketc.anktfw.di.module.Module
import tech.ketc.anktfw.di.module.singleton

object SampleModule : Module({
    singleton<ImageService>(::ImageServiceImpl)
})