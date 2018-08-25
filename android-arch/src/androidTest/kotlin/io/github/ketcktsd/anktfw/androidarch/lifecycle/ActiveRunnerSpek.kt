package io.github.ketcktsd.anktfw.androidarch.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import io.github.ketcktsd.anktfw.androidarch.lifecycle.OnActiveRunner
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@RunWith(JUnitPlatform::class)
class ActiveRunnerSpek : Spek({
    val owner = object : LifecycleOwner {
        private val mRegistry = LifecycleRegistry(this)
        override fun getLifecycle(): LifecycleRegistry = mRegistry
    }

    it("execute when active") {
        val lifecycle = owner.lifecycle
        lifecycle.markState(Lifecycle.State.CREATED)
        val runner = OnActiveRunner()

        runner.setOwner(owner)
        lifecycle.markState(Lifecycle.State.STARTED)
        var count = 0
        fun run() = runner.runOnActive { count++ }

        run()
        assertEquals(0, count)//not run
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        assertEquals(1, count)//run

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)//stop
        run()
        assertEquals(1, count)//not run
        for (i in 1..3) run()
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)//restart
        assertEquals(5, count)//run all
        run()
        assertEquals(6, count)//run

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        run()
        assertEquals(6, count)//not run
    }

    it("expect thrown IllegalStateException by runOnActive") {
        val runner = OnActiveRunner()
        assertFailsWith<IllegalStateException> { runner.runOnActive { print("not call") } }
    }

    it("expect thrown IllegalStateException by setOwner") {
        val runner = OnActiveRunner()
        runner.setOwner(owner)
        assertFailsWith<IllegalStateException> { runner.setOwner(owner) }
    }
})