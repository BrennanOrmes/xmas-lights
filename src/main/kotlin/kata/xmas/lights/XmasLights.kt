package kata.xmas.lights

class XmasLights(

) {
    constructor(allOn: Boolean) : this() {
        setUpLights(allOn)
    }

    private val lights = hashMapOf<Pair<Int, Int>, Light>()

    fun displayLights() {
        for (x in 0..999) {
            for (y in 0..999) {
                val light = getLight(x, y)
                if (light.isOn) {
                    print("*")
                } else {
                    print(" ")
                }
            }
            println()
        }
    }

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

    fun turnOffRange(from : Pair<Int, Int>, to : Pair<Int, Int>) {
        for (x in from.first..to.first) {
            for (y in from.second..to.second) {
                turnOff(x, y)
            }
        }
    }

    fun toggleRange(from : Pair<Int, Int>, to : Pair<Int, Int>) {
        for (x in from.first..to.first) {
            for (y in from.second..to.second) {
                toggle(x, y)
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
    val lights = XmasLights(false)

    lights.turnOnRange(Pair(887,9), Pair(959,629))
    lights.turnOffRange(Pair(539,243), Pair(559,965))
    lights.turnOffRange(Pair(370,819), Pair(676,868))
    lights.turnOffRange(Pair(145,40), Pair(370,997))
    lights.turnOffRange(Pair(301,3), Pair(808,453))
    lights.turnOnRange(Pair(351,678), Pair(951,908))
    lights.toggleRange(Pair(720,196), Pair(897,994))
    lights.toggleRange(Pair(831,394), Pair(904,860))
    lights.displayLights()
}

