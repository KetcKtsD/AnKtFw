package tech.ketc.anktfw.di

import tech.ketc.anktfw.di.module.Module
import tech.ketc.anktfw.di.module.each

object ServiceModule : Module({
    each<HttpURLConnectionFactory> { DefaultHttpURLConnectionFactory() }
})