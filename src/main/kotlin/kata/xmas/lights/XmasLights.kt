package kata.xmas.lights

class XmasLights(

) {
    constructor(allOn: Boolean) : this() {
        setUpLights(allOn)
    }

    private val lights = hashMapOf<Pair<Int, Int>, Light>()

    fun getLights(): HashMap<Pair<Int, Int>, Light> {
        return lights
    }

    fun turnOnRange(from : Pair<Int, Int>, to : Pair<Int, Int>) {
        for (x in from.first..to.first) {
            for (y in from.second..to.second) {
                turnOn(x, y)
            }
        }
    }

    fun turnOn(x: Int, y: Int): Light {
        val light = getLight(x, y)

        light.isOn = true

        return light
    }

    fun turnOff(x: Int, y: Int): Light {
        val light = getLight(x, y)

        light.isOn = false

        return light
    }

    fun toggle(x: Int, y: Int): Light {
        val light = getLight(x, y)

        light.isOn = !light.isOn

        return light
    }

    private fun setUpLights(isOn: Boolean) {
        for (x in 0..999) {
            for (y in 0..999) {
                lights[Pair(x, y)] = Light(x, y, isOn)
            }
        }
    }

    private fun getLight(x: Int, y: Int): Light {
        return lights[Pair(x, y)]!!
    }
}

class Light(
    val x: Int,
    val y: Int,
    var isOn: Boolean
)

fun main(vararg args: String) {
    for(arg in args) {
        println(arg)
    }
}

