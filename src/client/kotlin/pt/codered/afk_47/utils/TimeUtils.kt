package pt.codered.afk_47.utils

import java.util.*
import kotlin.math.max
import kotlin.math.roundToInt

object TimeUtils {
    private val random = Random()

    /**
     * Returns a random integer between min and max (inclusive).
     * Good for Casting delays.
     */
    fun getRandomDelay(minTicks: Int, maxTicks: Int): Int {
        return (minTicks..maxTicks).random()
    }

    /**
     * Returns a "Human-like" reaction time using a Bell Curve.
     * * @param averageTicks The ideal reaction time (e.g. 4 ticks = 200ms)
     * @param deviationTicks How much it varies (e.g. 1.5 ticks)
     */
    fun getHumanReaction(averageTicks: Double, deviationTicks: Double): Int {
        // nextGaussian() returns a number with mean 0.0 and deviation 1.0
        val gaussian = random.nextGaussian()

        // Scale it to our desired average and deviation
        val result = (averageTicks + (gaussian * deviationTicks)).roundToInt()

        // Ensure we never return 0 or negative numbers (instant reactions are impossible)
        return max(2, result)
    }
}