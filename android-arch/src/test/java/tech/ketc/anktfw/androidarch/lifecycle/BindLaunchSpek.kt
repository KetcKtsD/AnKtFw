package tech.ketc.anktfw.androidarch.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import tech.ketc.anktfw.androidarch.croutine.asyncResponse
import tech.ketc.anktfw.androidarch.croutine.defaultContext
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BindLaunchSpek : Spek({
    it("expect that the job is bound to LifecycleOwner") {
        runBlocking {
            val owner = object : LifecycleOwner {
                private val mRegistry = LifecycleRegistry(this)
                override fun getLifecycle(): LifecycleRegistry = mRegistry
            }

            val registry = owner.lifecycle
            registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

            var executed = false
            owner.bindLaunch(CommonPool) {
                asyncResponse(defaultContext) {
                    100
                }.await()
                executed = true
            }.join()
            assertTrue(executed)

            executed = false
            (0..100).forEach {
                owner.bindLaunch(CommonPool) {
                    println("execute 2")
                    asyncResponse(defaultContext) {
                        println("async start")
                        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                        println("owner destroyed")
                        100
                    }.await()
                    println("destroyed")
                    executed = true
                }.join()
                assertFalse(executed)
            }
        }
    }
})