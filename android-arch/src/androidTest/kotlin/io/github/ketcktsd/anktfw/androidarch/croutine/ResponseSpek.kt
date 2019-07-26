package io.github.ketcktsd.anktfw.androidarch.croutine

import org.junit.platform.runner.*
import org.junit.runner.*
import org.spekframework.spek2.*
import org.spekframework.spek2.style.specification.*
import kotlin.test.*

@RunWith(JUnitPlatform::class)
class ResponseSpek : Spek({
    describe("testing generate Success Or Failure") {
        it("expect that Success will return") {
            val result = runOrThrowCatching { 1 + 1 }
            assertTrue { result.isSuccess }
            assertEquals(2, result.getOrThrow())
        }

        it("expect that failure will return") {
            val exception = Exception()
            val result = runOrThrowCatching { throw exception }
            assertTrue { result.isFailure }
            assertEquals(exception, result.exceptionOrNull())
        }

        it("expect thrown IllegalStateException") {
            assertFailsWith<IllegalStateException> {
                runOrThrowCatching(IllegalArgumentException::class) { throw IllegalStateException() }
            }
        }

        it("expect that illegalArgumentException will throw") {
            val illegalArgumentException = IllegalArgumentException()
            val result = runOrThrowCatching(IllegalArgumentException::class) {
                throw illegalArgumentException
            }
            when {
                result.isFailure -> assertEquals(illegalArgumentException, result.exceptionOrNull())
                else -> assert(false) { "it\'s not Failure" }
            }
        }
    }
})
