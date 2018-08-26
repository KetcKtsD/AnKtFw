package io.github.ketcktsd.anktfw.androidarch.croutine

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.*

@RunWith(JUnitPlatform::class)
class ResponseSpek : Spek({
    describe("testing generate Success Or Failure") {
        it("expect that Success will return") {
            val result = generateResult { 1 + 1 }
            assertTrue { result.isSuccess }
            assertEquals(2, result.getOrThrow())
        }

        it("expect that failure will return") {
            val exception = Exception()
            val result = generateResult { throw exception }
            assertTrue { result.isFailure }
            assertEquals(exception, result.exceptionOrNull())
        }

        it("expect thrown IllegalStateException") {
            assertFailsWith<IllegalStateException> {
                generateResult(IllegalArgumentException::class) { throw IllegalStateException() }
            }
        }

        it("expect that illegalArgumentException will throw") {
            val illegalArgumentException = IllegalArgumentException()
            val result = generateResult(IllegalArgumentException::class) {
                throw illegalArgumentException
            }
            when {
                result.isFailure -> assertEquals(illegalArgumentException, result.exceptionOrNull())
                else -> assert(false) { "it\'s not Failure" }
            }
        }
    }
})
