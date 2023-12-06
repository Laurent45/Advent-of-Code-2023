package day06

import println
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val input = Path("src/main/resources/Day06_part1.txt").readLines()
    val times = input[0].toList()
    val distances = input[1].toList()

    times.indices.map {
        val time = times[it]
        val distance = distances[it]

        // Find x in (time - x) * x = distance => time * x - x * x = distance
        // Quadratic formula ax*x + b*x + c = 0 => x = (-b ± sqrt(b*b - 4*distance)) / 2 * a
        // a = 1, b = -time, c = distance
        val minSpeed = ((time - sqrt(time.toDouble().pow(2.0) - 4 * distance)) / 2).toLong() + 1

        val maxSpeed = time - minSpeed

        maxSpeed - minSpeed + 1
    }.reduce(Long::times).println()
}

fun String.toList(): List<Long> = this.substringAfter(":").split(" ").filter { it.isNotBlank() }.map(String::toLong)
