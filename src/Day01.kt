fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf(String::getCalibrationValue)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

fun String.getCalibrationValue(): Int {
    var first: Char? = null
    var last: Char? = null
    for (i in this.indices) {
        if (first == null && this[i].isDigit()) first = this[i]
        if (last == null && this[this.length - 1 - i].isDigit()) last = this[this.length - 1 - i]
        if (first != null && last != null) return "$first$last".toInt()
    }
    return 0
}
