package io.github.ketcktsd.anktfw.di.module

import io.github.ketcktsd.anktfw.di.container.*
import org.jetbrains.spek.api.*
import org.jetbrains.spek.api.dsl.*
import org.junit.platform.runner.*
import org.junit.runner.*
import kotlin.test.*

@RunWith(JUnitPlatform::class)
class ModuleSpek : Spek({
    describe("IllegalArgumentException is thrown at the time of Module create") {
        it("by singleton()") {
            assertFailsWith<IllegalArgumentException> {
                module {
                    singleton<DependencyA> { DependencyAImpl() }
                    singleton<DependencyA> { DependencyAImpl() }
                }
            }
        }

        it("by lazySingleton()") {
            assertFailsWith<IllegalArgumentException> {
                module {
                    lazySingleton<DependencyA> { DependencyAImpl() }
                    lazySingleton<DependencyA> { DependencyAImpl() }
                }
            }
        }

        it("by each()") {
            assertFailsWith<IllegalArgumentException> {
                module {
                    each<DependencyA> { DependencyAImpl() }
                    each<DependencyA> { DependencyAImpl() }
                }
            }
        }

        it("by lazyEach()") {
            assertFailsWith<IllegalArgumentException> {
                module {
                    lazyEach<DependencyA> { DependencyAImpl() }
                    lazyEach<DependencyA> { DependencyAImpl() }
                }
            }
        }
    }

    describe("IllegalArgumentException is thrown at the dependency resolve") {
        it("added by singleton()") {
            val module = module {
                singleton<DependencyA> { DependencyAImpl() }
            }
            assertFailsWith<IllegalArgumentException> { module.resolve(DependencyB::class) }
        }

        it("added by lazySingleton()") {
            val module = module {
                lazySingleton<DependencyA> { DependencyAImpl() }
            }
            assertFailsWith<IllegalArgumentException> { module.resolve(DependencyB::class) }
        }

        it("added by each()") {
            val module = module {
                each<DependencyA> { DependencyAImpl() }
            }
            assertFailsWith<IllegalArgumentException> { module.resolve(DependencyB::class) }
        }

        it("added by lazyEach()") {
            val module = module {
                lazyEach<DependencyA> { DependencyAImpl() }
            }
            assertFailsWith<IllegalArgumentException> { module.resolve(DependencyB::class) }
        }
    }

    describe("scope test") {
        fun scopeTest(module1: Module, module2: Module) {
            val target1 = InjectionTarget(module1)
            assertEquals(target1.dependencyA1, target1.dependencyA2)
            assertNotEquals(target1.dependencyB1, target1.dependencyB2)

            val target2 = InjectionTarget(module2)
            assertNotEquals(target1.dependencyA1, target2.dependencyA1)
            assertNotEquals(target1.dependencyB1, target2.dependencyB1)
        }

        it("by not lazyXX") {
            fun simpleModule() = module {
                singleton<DependencyA> { DependencyAImpl() }
                each<DependencyB> { DependencyBImpl() }
            }
            scopeTest(simpleModule(), simpleModule())
        }

        it("by lazyXX") {
            fun simpleModule() = module {
                lazySingleton<DependencyA> { DependencyAImpl() }
                lazyEach<DependencyB> { DependencyBImpl() }
            }
            scopeTest(simpleModule(), simpleModule())
        }
    }

    describe("initialization timing test") {
        it("non lazy") {
            var isInitializedA = false
            var isInitializedB = false

            val nonLazyModule = module {
                singleton<DependencyA> { DependencyAImpl { isInitializedA = true } }
                assertTrue(isInitializedA)
                each<DependencyB> { DependencyBImpl { isInitializedB = true } }
                assertFalse(isInitializedB)
            }

            assertFalse(isInitializedB)
            InjectionTarget(nonLazyModule)
            assertTrue(isInitializedB)
        }

        it("lazy") {
            var isInitializedA = false
            var isInitializedB = false

            val lazyModule = module {
                lazySingleton<DependencyA> { DependencyAImpl { isInitializedA = true } }
                assertFalse(isInitializedA)
                lazyEach<DependencyB> { DependencyBImpl { isInitializedB = true } }
                assertFalse(isInitializedB)
            }

            val target = InjectionTarget(lazyModule)

            assertFalse(isInitializedA)
            target.dependencyA1
            assertTrue(isInitializedA)

            assertFalse(isInitializedB)
            target.dependencyB1
            assertTrue(isInitializedB)
        }
    }
})

private interface DependencyA
private interface DependencyB

private class DependencyAImpl(init: () -> Unit = {}) : DependencyA {
    init {
        init()
    }
}

private class DependencyBImpl(init: () -> Unit = {}) : DependencyB {
    init {
        init()
    }
}

private class InjectionTarget(override val module: Module) : InjectionSupport {
    val dependencyA1: DependencyA by resolve()
    val dependencyA2: DependencyA by resolve()
    val dependencyB1: DependencyB by resolve()
    val dependencyB2: DependencyB by resolve()
}
