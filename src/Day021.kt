fun main() {
    fun part1(input: List<String>): Int {
        return Day021(input).sumOfIdOfPossibleGame()
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day02_part1_test")
    check(part1(testInput1) == 8)

    val input1 = readInput("Day02_part1")
    part1(input1).println()


    fun part2(input: List<String>): Int {
        return Day022(input).computeMinimalCubesByColorByGame()
    }

    // test if implementation meets criteria from the description, like:
    val testInput2 = readInput("Day02_part2_test")
    check(part2(testInput2) == 2286)

    val input = readInput("Day02_part2")
    part2(input).println()
}

enum class Color { RED, GREEN, BLUE }

class Day021(private val input: List<String>) {
    private val cubes = mapOf(Color.RED to 12, Color.GREEN to 13, Color.BLUE to 14)

    fun sumOfIdOfPossibleGame(): Int {
        return input.mapNotNull(::getPossibleGameIdOrNull).sum()
    }

    private fun getPossibleGameIdOrNull(line: String): Int? {
        val match = """Game (\d*): (.*)""".toRegex()
            .find(line) ?: error("Invalid line: $this")

        val (gameId, sets) = match.destructured

        val correctSets = Regex("""(\d*) (green|blue|red)""")
            .findAll(sets)
            .all {
                val (n, color) = it.destructured
                cubes[Color.valueOf(color.uppercase())]!! >= n.toInt()
            }

        return if (correctSets) gameId.toInt() else null
    }
}

class Day022(private val input: List<String>) {
    private var colorByMinimum: Map<Color, Int>? = null

    init {
        colorByMinimum = this.input
            .map(::colorByMinimumCube)
            .flatMap { it.entries }
            .groupBy({ it.key }, { it.value })
            .mapValues { it.value.max() }
    }

    fun computeMinimalCubesByColorByGame(): Int {
        return input
            .map(::colorByMinimumCube)
            .filter {
                it.entries.all { (color, n) ->
                    n <= colorByMinimum?.get(color)!!
                }
            }
            .sumOf {
                it.values.reduce { acc, i -> acc * i }
            }
    }

    private fun colorByMinimumCube(line: String): Map<Color, Int> {
        return Regex("""(\d*) (green|blue|red)""")
            .findAll(line)
            .map { match ->
                val (n, color) = match.destructured
                Pair(Color.valueOf(color.uppercase()), n.toInt())
            }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.max() }
    }
}