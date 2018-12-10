package io.github.ketcktsd.anktfw.androidarch.lifecycle

import androidx.lifecycle.*
import io.github.ketcktsd.anktfw.androidarch.croutine.*
import kotlinx.coroutines.*
import org.jetbrains.spek.api.*
import org.jetbrains.spek.api.dsl.*
import org.junit.platform.runner.*
import org.junit.runner.*
import kotlin.test.*

@RunWith(JUnitPlatform::class)
class BindLaunchSpek : Spek({
    fun owner() = object : LifecycleOwner {
        private val mRegistry = LifecycleRegistry(this)
        override fun getLifecycle(): LifecycleRegistry = mRegistry
    }

    it("expected to be executed without problems") {
        runBlocking {
            val owner = owner()
            val registry = owner.lifecycle
            val scope = LifecycleScope(owner, GlobalScope)
            registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
            @Suppress("ForEachParameterNotUsed")
            (1..100).forEach {
                var executed = false
                scope.bindLaunch {
                    val result = asyncResult(defaultContext) {
                        100
                    }.await()
                    result.onSuccess { executed = true }
                }.join()
                assertTrue(executed)
            }
        }
    }

    it("expect that the job is bound to LifecycleOwner") {
        runBlocking {
            @Suppress("ForEachParameterNotUsed")
            (1..100).forEach {
                var executed = false
                val owner = owner()
                val registry = owner.lifecycle
                val scope = LifecycleScope(owner, GlobalScope)
                registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
                scope.bindLaunch {
                    asyncResult(defaultContext) {
                        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                        100
                    }.await()
                    executed = true
                }.join()
                assertFalse(executed)
            }
        }
    }
})
