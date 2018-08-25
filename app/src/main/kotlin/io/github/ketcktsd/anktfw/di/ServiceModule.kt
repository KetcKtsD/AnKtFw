package io.github.ketcktsd.anktfw.di

import io.github.ketcktsd.anktfw.di.module.Module
import io.github.ketcktsd.anktfw.di.module.each

object ServiceModule : Module({
    each<HttpURLConnectionFactory> { DefaultHttpURLConnectionFactory() }
})
