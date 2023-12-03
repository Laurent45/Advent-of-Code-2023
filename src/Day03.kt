fun main() {
    fun part1(input: List<String>): Int {
        val (indexLineToNumbers, symbols) = input.parse { !it.isDigit() && !it.isLetter() && it != '.' }
        val maxLines = input.size
        val maxColumns = input[0].length

        return symbols
            .flatMap { it.numbersAround(maxLines, maxColumns, indexLineToNumbers) }
            .toSet()
            .sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        val (indexLineToNumbers, symbols) = input.parse { it == '*' }
        val maxLines = input.size
        val maxColumns = input[0].length

        return symbols
            .map { it.numbersAround(maxLines, maxColumns, indexLineToNumbers) }
            .mapNotNull { if (it.size == 2) Pair(it.first(), it.last()) else null }
            .toSet()
            .sumOf { it.first.value * it.second.value }
    }

    // test if implementation meets criteria from the description, like:
    val testInputPart1 = readInput("Day03_part1_test")
    check(part1(testInputPart1) == 4361)
    val testInputPart2 = readInput("Day03_part2_test")
    check(part2(testInputPart2) == 467835)

    val inputParty1 = readInput("Day03_part1")
    part1(inputParty1).println()
    val inputParty2 = readInput("Day03_part2")
    part2(inputParty2).println()
}

data class Number(val value: Int, val indexLine: Int, val indexColumnRange: IntRange)
data class Position(val indexLine: Int, val indexColumn: Int)

fun Position.numbersAround(maxLines: Int, maxColumns: Int, indexLineToNumbers: Map<Int, List<Number>>): Set<Number> =
    this.indexLine.closestIndex(maxLines)
        .flatMap { indexLine -> this.indexColumn.closestIndex(maxColumns).map { Position(indexLine, it) } }
        .mapNotNull { position -> indexLineToNumbers[position.indexLine]?.find { position.indexColumn in it.indexColumnRange } }
        .toSet()

fun List<String>.parse(isSymbol: (c: Char) -> Boolean): Pair<Map<Int, List<Number>>, List<Position>> {
    val indexLineToNumbers: MutableMap<Int, MutableList<Number>> = mutableMapOf()
    val symbols: MutableList<Position> = mutableListOf()

    forEachIndexed { indexLine, line ->
        var indexColumn = 0
        while (indexColumn in line.indices) {
            when {
                line[indexColumn].isDigit() -> {
                    // "...123..." => Number(123, lineNumber, 3..5)
                    var j = indexColumn + 1
                    while (j in line.indices && line[j].isDigit()) j++
                    indexLineToNumbers.putIfAbsent(indexLine, mutableListOf())
                    indexLineToNumbers[indexLine]!!.add(
                        Number(line.substring(indexColumn, j).toInt(), indexLine, indexColumn..<j)
                    )
                    indexColumn = j
                }

                else -> {
                    // "...123...*.." => Symbol()
                    if (isSymbol(line[indexColumn])) symbols.add(Position(indexLine, indexColumn))
                    indexColumn++
                }
            }
        }
    }
    return Pair(indexLineToNumbers, symbols)
}

fun Int.closestIndex(max: Int, min: Int = 0) = (this - 1).coerceAtLeast(min)..(this + 1).coerceAtMost(max)
