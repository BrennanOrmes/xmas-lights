package kata.xmas.lights

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

internal class XmasLightsTest {

    @ParameterizedTest
    @CsvSource(
        "0,0",
        "1,2",
        "2,3",
        "3,4",
        "4,5",
        "5,6",
        "6,7",
        "7,8",
        "8,9",
        "9,10",
    )
    fun `should turn on lights`(
        x: Int,
        y: Int
    ) {
        val xmasLights = XmasLights(false)

        val light = xmasLights.turnOn(x, y)

        assertEquals(x, light.x)
        assertEquals(y, light.y)
        assertEquals(true, light.isOn())
    }

    @ParameterizedTest
    @CsvSource(
        "0,0,1",
        "1,2,2",
        "2,3,3",
        "3,4,4",
        "4,5,5",
        "5,6,6",
        "6,7,7",
        "7,8,8",
        "8,9,9",
        "9,10,10",
        "10,11,1000",
    )
    fun `should set brightness for a light to expected level`(
        x: Int,
        y: Int,
        brightness: Int
    ) {
        val xmasLights = XmasLights(false)

        for (i in 1..brightness) {
            xmasLights.turnOn(x, y)
        }

        val light = xmasLights.getLights()[Pair(x, y)]!!

        assertEquals(brightness, light.brightness)
    }

    @ParameterizedTest
    @CsvSource(
        "0,0",
        "1,2",
        "2,3",
        "3,4",
        "4,5",
        "5,6",
        "6,7",
        "7,8",
        "8,9",
        "9,10",
    )
    fun `should turn off lights`(
        x: Int,
        y: Int
    ) {
        val xmasLights = XmasLights(true)

        val result = xmasLights.turnOff(x, y)

        assertEquals(x, result.x)
        assertEquals(y, result.y)
        assertEquals(false, result.isOn())
    }

    @Test
    fun `should not set brightness for a light less than 0`() {
        val xmasLights = XmasLights(true)


        val light = xmasLights.turnOff(0, 0)

        assertEquals(0, light.brightness)

        val lightTurnedOffAgain = xmasLights.turnOff(0, 0)

        assertEquals(0, lightTurnedOffAgain.brightness)
    }

    @Test
    fun `should only turn off the expected lights`() {
        val xmasLights = XmasLights(true)

        xmasLights.turnOff(0, 0)

        val otherLights = xmasLights.getLights().values.filter { it.x != 0 && it.y != 0 }
        val light = xmasLights.getLights().values.first { it.x == 0 && it.y == 0 }

        otherLights.forEach {
            assertEquals(true, it.isOn())
        }

        assertEquals(false, light.isOn())
    }

    @Test
    fun `should turn 0,0 to 999,999 to on`() {
        val xmasLights = XmasLights(false)

        xmasLights.turnOnRange(Pair(0, 0), Pair(999, 999))

        xmasLights.getLights().values.forEach {
            assertEquals(true, it.isOn())
        }
    }

    @Test
    fun `should turn 0,0 to 10,10 to on, with rest still being off`() {
        val xmasLights = XmasLights(false)

        xmasLights.turnOnRange(Pair(0, 0), Pair(10, 10))

        val otherLights = xmasLights.getLights().values.filter { it.x !in 0..10 && it.y !in 0..10 }
        val onLights = xmasLights.getLights().values.filter { it.x in 0..10 && it.y in 0..10 }

        otherLights.forEach {
            assertEquals(false, it.isOn())
        }

        onLights.forEach {
            assertEquals(true, it.isOn())
        }
    }

    @Test
    fun `should turn 0,0 to 999,999 to off`() {
        val xmasLights = XmasLights(true)

        xmasLights.turnOffRange(Pair(0, 0), Pair(999, 999))

        xmasLights.getLights().values.forEach {
            assertEquals(false, it.isOn())
        }
    }

    @Test
    fun `should turn 0,0 to 10,10 to off, with rest still being on`() {
        val xmasLights = XmasLights(true)

        xmasLights.turnOffRange(Pair(0, 0), Pair(10, 10))

        val otherLights = xmasLights.getLights().values.filter { it.x !in 0..10 && it.y !in 0..10 }
        val offLights = xmasLights.getLights().values.filter { it.x in 0..10 && it.y in 0..10 }

        otherLights.forEach {
            assertEquals(true, it.isOn())
        }

        offLights.forEach {
            assertEquals(false, it.isOn())
        }
    }

    @Nested
    inner class ToggleTests {
        @ParameterizedTest
        @ValueSource(
            booleans = [true, false]
        )
        fun `should toggle 0,0 to 999,999`(
            initialState: Boolean
        ) {
            val xmasLights = XmasLights(initialState)

            xmasLights.toggleRange(Pair(0, 0), Pair(999, 999))

            xmasLights.getLights().values.forEach {
                assertEquals(true, it.isOn())
            }
        }

        @ParameterizedTest
        @ValueSource(
            booleans = [true, false]
        )
        fun `should toggle 0,0 to 10,10, with rest not being toggled`(
            initialState: Boolean
        ) {
            val xmasLights = XmasLights(initialState)

            xmasLights.toggleRange(Pair(0, 0), Pair(10, 10))

            val otherLights = xmasLights.getLights().values.filter { it.x !in 0..10 && it.y !in 0..10 }
            val toggledLights = xmasLights.getLights().values.filter { it.x in 0..10 && it.y in 0..10 }

            val expectedLightBrightness = if (initialState) 1 else 0
            val expectedLightBrightnessToggled = if (initialState) 3 else 2

            otherLights.forEach {
                assertEquals(expectedLightBrightness, it.brightness)
            }

            toggledLights.forEach {
                assertEquals(expectedLightBrightnessToggled, it.brightness)
            }
        }

        @Test
        fun `should toggle 0,0 brightness to 2`() {
            val xmasLights = XmasLights(false)

            xmasLights.toggle(0, 0)

            val otherLights = xmasLights.getLights().values.filter { it.x != 0 && it.y != 0 }
            val light = xmasLights.getLights().values.first { it.x == 0 && it.y == 0 }

            otherLights.forEach {
                assertEquals(false, it.isOn())
            }

            assertEquals(true, light.isOn())
        }

        @ParameterizedTest
        @CsvSource(
            "0,0,1",
            "1,2,2",
            "2,3,3",
            "3,4,4",
            "4,5,5",
            "5,6,6",
            "6,7,7",
            "7,8,8",
            "8,9,9",
            "9,10,10",
            "10,11,1000",
        )
        fun `should toggle brightness for a light to expected level`(
            x: Int,
            y: Int,
            toggledAmount: Int
        ) {
            val xmasLights = XmasLights(false)

            for (i in 1..toggledAmount) {
                xmasLights.toggle(x, y)
            }

            val light = xmasLights.getLights()[Pair(x, y)]!!

            assertEquals(toggledAmount * 2, light.brightness)
        }
    }
}
