package day04

import println
import readInput

fun main() {

    val expectedPart1 = 13
    val expectedPart2 = 30

    fun part1(input: List<String>): Int {
        return input
            .map(String::toCard)
            .sumOf(Card::points)
    }

    fun part2(input: List<String>): Int {
        return input
            .map(String::toCard)
            .totalScratchcards()
    }

    val testInputPart1 = readInput("Day04_part1_test")
    check(part1(testInputPart1) == expectedPart1)
    val inputParty1 = readInput("Day04_part1")
    part1(inputParty1).println()

    val testInputPart2 = readInput("Day04_part2_test")
    check(part2(testInputPart2) == expectedPart2)
    val inputParty2 = readInput("Day04_part2")
    part2(inputParty2).println()
}

class Card(
    private val winNumbers: List<Int>,
    private val scratchNumbers: List<Int>,
    var numbersOfCopy: Int = 1
) {

    val points: Int
        get() = scratchNumbers
            .filter { winNumbers.contains(it) }
            .foldIndexed(0) { idx, acc, _ -> if (idx == 0) 1 else acc * 2 }

    val matchingNumbers: Int
        get() = scratchNumbers.count { winNumbers.contains(it) }
}

fun String.toCard(): Card {
    // Line example: Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
    val match = Regex("""Card\s+(\d+):(?<winningNumbers>[\s\d]+) \|(?<scratchNumbers>[\s\d]+)""")
        .matchEntire(this) ?: error("Invalid card line $this")

    val winNumbers = match.groups["winningNumbers"]!!.value.toIntList()
    val scratchNumbers = match.groups["scratchNumbers"]!!.value.toIntList()

    return Card(winNumbers, scratchNumbers)
}

fun List<Card>.totalScratchcards(): Int = this.mapIndexed { id, card ->
        val matchingNumber = card.matchingNumbers
        val rangeOfNextIds = (id + 1)..(id + matchingNumber).coerceAtMost(this.size - 1)

        for (nextId in rangeOfNextIds) this[nextId].numbersOfCopy += card.numbersOfCopy
        card.numbersOfCopy
    }.sum()

fun String.toIntList():List<Int> = this.trimStart().split(Regex("""\s+""")) .map(String::toInt)