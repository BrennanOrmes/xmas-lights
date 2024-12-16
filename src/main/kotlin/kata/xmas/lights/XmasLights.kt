package kata.xmas.lights

class XmasLights {
    fun greeting() = "Hello World"
}

fun main(vararg args: String) {
    for(arg in args) {
        println(arg)
    }
    println(XmasLights().greeting())
}


