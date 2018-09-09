package io.github.ketcktsd.anktfw.androidarch.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import io.github.ketcktsd.anktfw.androidarch.croutine.execAsync
import io.github.ketcktsd.anktfw.androidarch.croutine.defaultContext
import kotlinx.coroutines.runBlocking
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.coroutines.coroutineContext
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
            registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
            (1..100).forEach { _ ->
                var executed = false
                owner.bindLaunch(coroutineContext) {
                    val result = execAsync(defaultContext()) {
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
            (1..100).forEach {
                var executed = false
                val owner = owner()
                val registry = owner.lifecycle
                registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
                owner.bindLaunch(coroutineContext) {
                    execAsync(defaultContext()) {
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
