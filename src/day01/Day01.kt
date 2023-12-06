package day01

import println
import readInput
import java.lang.StringBuilder

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .sumOf(String::getCalibrationValueFromFirstAndLastDigitChar)
    }

    fun part2(input: List<String>): Int {
        return input
            .map(String::transformSpelledDigitWithDigitChar)
            .sumOf(String::getCalibrationValueFromFirstAndLastDigitChar)
    }

    // test if implementation meets criteria from the description, like:
    val testInputPart1 = readInput("Day01_part1_test")
    check(part1(testInputPart1) == 142)
    val testInputPart2 = readInput("Day01_part2_test")
    check(part2(testInputPart2) == 281)

    val inputParty1 = readInput("Day01_part1")
    part1(inputParty1).println()
    val inputParty2 = readInput("Day01_part2")
    part2(inputParty2).println()
}

fun String.getCalibrationValueFromFirstAndLastDigitChar(): Int {
    var first: Char? = null
    var last: Char? = null
    for (i in this.indices) {
        if (first == null && this[i].isDigit()) first = this[i]
        if (last == null && this[this.length - 1 - i].isDigit()) last = this[this.length - 1 - i]
        if (first != null && last != null) return "$first$last".toInt()
    }
    return 0
}

fun String.transformSpelledDigitWithDigitChar(): String {
    val spelledToCharDigit = mapOf(
        "zero" to '0',
        "one" to '1',
        "two" to '2',
        "three" to '3',
        "four" to '4',
        "five" to '5',
        "six" to '6',
        "seven" to '7',
        "eight" to '8',
        "nine" to '9',
    )
    val transform = StringBuilder()

    for (i in indices) {
        val match = spelledToCharDigit.entries.find { this.startsWith(it.key, i) }
        transform.append(match?.value ?: this[i])
    }
    return transform.toString()
}
