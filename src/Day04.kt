fun main() {

    val expectedPart1 = 13
    val expectedPart2 = 30

    fun part1(input: List<String>): Int {
        return input
            .mapNotNull(::card)
            .sumOf(Card::pointsWined)
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInputPart1 = readInput("Day04_part1_test")
    check(part1(testInputPart1) == expectedPart1)
    val testInputPart2 = readInput("Day04_part2_test")
    check(part2(testInputPart2) == expectedPart2)

    val inputParty1 = readInput("Day04_part1")
    part1(inputParty1).println()
/*
    val inputParty2 = readInput("Day04_part2")
    part2(inputParty2).println()
*/
}

data class Card(val id: Int, val winNumbers: List<Int>, val myNumbers: List<Int>)

fun card(line: String): Card? {
//    TODO("Check entire line with regex")
    val cardId = Regex("""\d+""")
        .find(line.substringBefore(":"))
        ?.value ?: return null

    val numbers = line.substringAfter(": ").split(" | ")
        .map {
            Regex("""\d+""")
                .findAll(it)
                .map(MatchResult::value)
                .map(String::toInt)
                .toList()
        }
        .toList()

    return Card(cardId.toInt(), numbers[0], numbers[1])
}

fun Card.pointsWined(): Int {
    return myNumbers
        .filter { winNumbers.contains(it) }
        .foldIndexed(0) { idx, acc, _ -> if (idx == 0) 1 else acc * 2 }
}