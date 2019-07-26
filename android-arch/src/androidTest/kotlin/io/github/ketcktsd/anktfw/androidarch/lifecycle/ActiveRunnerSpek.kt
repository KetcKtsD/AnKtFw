package io.github.ketcktsd.anktfw.androidarch.lifecycle

import androidx.lifecycle.*
import org.junit.platform.runner.*
import org.junit.runner.*
import org.spekframework.spek2.*
import org.spekframework.spek2.style.specification.*
import kotlin.test.*

@RunWith(JUnitPlatform::class)
class ActiveRunnerSpek : Spek({
    val owner = object : LifecycleOwner {
        private val mRegistry = LifecycleRegistry(this)
        override fun getLifecycle(): LifecycleRegistry = mRegistry
    }
    describe("ActiveRunner") {

        it("execute when active") {
            val lifecycle = owner.lifecycle

            val runner = OnActiveRunner(owner)
            var count = 0
            var callCount = 0
            fun run() {
                println("call runOnActive: callCount=${callCount++}, executedCount=$count")
                runner.runOnActive {
                    println("execute: ${++count}")
                }
            }

            fun handleLifecycleEvent(event: Lifecycle.Event) = lifecycle.handleLifecycleEvent(event.also(::println))

            //not safe
            run()
            run()
            assertEquals(0, count)

            //on safe
            handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
            assertEquals(2, count)
            //safe
            run()
            assertEquals(3, count)
            run()
            assertEquals(4, count)

            //Follow the Lifecycle
            handleLifecycleEvent(Lifecycle.Event.ON_START)
            handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

            //on not safe
            handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            //not safe
            run()
            run()
            assertEquals(4, count)

            //on safe
            handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
            assertEquals(6, count)
            //safe
            run()
            assertEquals(7, count)
            run()
            assertEquals(8, count)

            //on not safe (destroy)
            handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            run()
            run()
            assertEquals(8, count)

            //no reaction
            handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
            assertEquals(8, count)
        }
    }
})
