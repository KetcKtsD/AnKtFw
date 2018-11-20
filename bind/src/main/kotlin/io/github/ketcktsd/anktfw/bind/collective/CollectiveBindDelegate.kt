package io.github.ketcktsd.anktfw.bind.collective

import io.github.ketcktsd.anktfw.bind.property.ObservableProperty

interface CollectiveBindDelegate<T> {
    fun set(value: T)
    fun get(): T
}

internal class CollectiveBindDelegateImpl<T>(private var value: T,
                                    private val observables: Array<ObservableProperty<T>>) : CollectiveBindDelegate<T> {

    private var mInitialized = false

    init {
        observables.forEach {
            it.addListener(::onChange)
        }
    }

    override fun set(value: T) {
        initialize()
        this.value = value
        observables.forEach { it.value = value }
    }

    override fun get(): T {
        initialize()
        return value
    }

    private fun onChange(value: T) {
        this.value = value
        observables.forEach { it.value = value }
        if (!mInitialized) mInitialized = true
    }

    private fun initialize() {
        if (mInitialized) return
        mInitialized = true
        observables.forEach { it.value = value }
    }
}
