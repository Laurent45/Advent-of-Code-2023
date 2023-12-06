package day06

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.pow
import kotlin.math.sqrt


fun main() {
    val input = Path("src/main/resources/Day06_part2.txt").readLines()
    val time = input[0].aggregateToLong()
    val distance = input[1].aggregateToLong()


    // Find x in (time - x) * x = distance => time * x - x * x = distance
    // Quadratic formula ax*x + b*x + c = 0 => x = (-b ± sqrt(b*b - 4*distance)) / 2 * a
    // a = 1, b = -time, c = distance
    val minSpeed = ((time - sqrt(time.toDouble().pow(2.0) - 4 * distance)) / 2).toLong() + 1

    val maxSpeed = time - minSpeed

    println(maxSpeed - minSpeed + 1)
}

fun String.aggregateToLong(): Long = this
    .substringAfter(":")
    .split(" ")
    .filter { it.isNotBlank() }
    .fold(0L) { acc, string -> (acc * (10.0.pow(string.length).toLong())) + string.toLong() }

