package tech.ketc.anktfw.di.module

import tech.ketc.anktfw.di.module.container.Container
import java.util.*
import kotlin.reflect.KClass


abstract class Module(dsl: DependencyDsl.() -> Unit) {
    private val dependencyContainer = DependencyContainer().apply(dsl)

    fun <T : Any> inject(clazz: KClass<T>): Container<T> = dependencyContainer.get(clazz)
}

class Hoge

class Fuga {
    val randomInt = Random().nextInt(10).toString()
}

class HogeModule : Module({
    Hoge::class resolutionOf singleton { Hoge() }
    Fuga::class resolutionOf each { Fuga() }
})

val module = HogeModule()
val hoge by module.inject(Hoge::class)
val fuga by module.inject(Fuga::class)
fun main(args: Array<String>) {
    (1..3).forEach { println(hoge.toString()) }
    (1..10).forEach { println(fuga.randomInt) }
}