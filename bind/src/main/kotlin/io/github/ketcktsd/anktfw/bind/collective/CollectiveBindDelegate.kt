package io.github.ketcktsd.anktfw.bind.collective

import io.github.ketcktsd.anktfw.bind.property.BindableProperty

class CollectiveBindDelegate<T>(private var value: T,
                                private val bindables: Array<BindableProperty<T>>) {

    private var mInitialized = false

    init {
        bindables.forEach {
            it.onChange = ::onChange
        }
    }

    fun set(value: T) {
        initialize()
        this.value = value
        bindables.forEach { it.value = value }
    }

    fun get(): T {
        initialize()
        return value
    }

    private fun onChange(property: BindableProperty<T>) {
        this.value = property.value
        bindables.asSequence()
                .filterNot { it == property }
                .forEach { it.set(value) }
        if (!mInitialized) mInitialized = true
    }

    private fun initialize() {
        if (mInitialized) return
        mInitialized = true
        bindables.forEach { it.value = value }
    }
}
