package io.github.ketcktsd.anktfw.di

import io.github.ketcktsd.anktfw.di.module.each
import io.github.ketcktsd.anktfw.di.module.module

val serviceModule = module {
    each<HttpURLConnectionFactory> { DefaultHttpURLConnectionFactory() }
}
