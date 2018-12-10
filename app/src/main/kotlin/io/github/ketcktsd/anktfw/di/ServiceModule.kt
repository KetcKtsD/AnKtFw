package io.github.ketcktsd.anktfw.di

import io.github.ketcktsd.anktfw.di.module.*

val serviceModule = module {
    each<HttpURLConnectionFactory> { DefaultHttpURLConnectionFactory() }
}
