package tech.ketc.anktfw.androidarch.croutine

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.*

@RunWith(JUnitPlatform::class)
class ResponseSpek : Spek({
    describe("testing generate Response") {
        it("expect that Success will return") {
            val response = generateResponse { 1 + 1 }
            assertTrue { response is Success }
            assertEquals(2, response.success()!!.result)
        }

        it("expect that Failure will return") {
            val exception = Exception()
            val response = generateResponse { throw exception }
            assertTrue { response is Failure }
            assertEquals(exception, response.failure()!!.error)
        }

        it("expect thrown IllegalStateException") {
            assertFailsWith<IllegalStateException> {
                generateResponse(IllegalArgumentException::class) { throw IllegalStateException() }
            }
        }

        it("expect that illegalArgumentException will return") {
            val illegalArgumentException = IllegalArgumentException()
            val response = generateResponse(IllegalArgumentException::class) { throw illegalArgumentException }
            when (response) {
                is Failure -> assertEquals(illegalArgumentException, response.error)
                else -> assert(false) { "it\'s not Failure" }
            }
        }
    }

    describe("testing utility functions") {
        it("expect that not-null will return") {
            val success = generateResponse { 1 + 1 }.success()
            assertNotNull(success)
            assertEquals(2, success!!.result)
            val exception = Exception()
            val failure = generateResponse { throw exception }.failure()
            assertNotNull(failure)
            assertEquals(exception, failure!!.error)
        }

        it("expect that null will return") {
            val success = generateResponse { throw  Exception() }.success()
            assertNull(success)
            val failure = generateResponse { 1 + 1 }.failure()
            assertNull(failure)
        }

        it("expect the lambda expression to be executed") {
            var executed = false
            generateResponse { 1 + 1 }.successIf {
                assertEquals(2, it)
                executed = true
            }
            assertTrue(executed)
            executed = false
            generateResponse { throw Exception() }.failureIf {
                assertEquals(Exception::class, it::class)
                executed = true
            }
            assertTrue(executed)
        }
        it("expect that lambda expressions will not be executed") {
            var executed = false
            generateResponse { throw Exception() }.successIf {
                executed = true
            }
            assertFalse(executed)
            executed = false
            generateResponse { 1 + 1 }.failureIf {
                executed = true
            }
            assertFalse(executed)
        }

        it("expect that default-value will return") {
            val response = generateResponse<Int> { throw Exception() }
            val defaultValue = 3
            val value = response.getOrElse { defaultValue }
            assertEquals(defaultValue, value)
        }

        it("expect that default-value will not return") {
            val response = generateResponse { 100 + 100 }
            val defaultValue = 3
            val value = response.getOrElse { defaultValue }
            assertNotEquals(defaultValue, value)
            assertEquals(200, value)
        }

        it("expect that transform-value will return") {
            val success = generateResponse { 100 }.success()!!
            val value = success.map { it * 100 }
            assertEquals(100 * 100, value)
        }
    }
})