package day11

import println
import readInput
import kotlin.math.abs

// part1: 9521550
// part2: 298932923702

// number of rows or columns expanded when we are in an empty space
// part1: 2
// part2: 1000000
const val factorExpansion = 1000000L
fun main() {

    val image = readInput("Day11_part1")

    val rowsWithEmptySpace = MutableList(image.size - 1) { it }
    val colsWithEmptySpace = MutableList(image[0].length - 1) { it }

    image.flatMapIndexed { idx, line ->
        val galaxies = mutableListOf<Position>()

        for ((idRow, chr) in line.withIndex()) {
            if (chr == '#') {
                galaxies.add(Position(idx, idRow))
                rowsWithEmptySpace.remove(idx)
                colsWithEmptySpace.remove(idRow)
            }
        }

        galaxies
    }.toList().let {

        val pairs = mutableListOf<Pair<Position, Position>>()

        for (idx in it.indices) {
            for (nextIdx in (idx + 1) until it.size) {
                pairs.add(Pair(it[idx], it[nextIdx]))
            }
        }

        pairs
    }
        .sumOf { it.shortestPath(rowsWithEmptySpace, colsWithEmptySpace) }
        .println()

}

data class Position(val row: Int, val col: Int)

fun Pair<Position, Position>.shortestPath(rowsEmptySpace: List<Int>, colEmptySpace: List<Int>): Long {
    val stepLine = abs(this.first.row - this.second.row)
    val stepCol = abs(this.first.col - this.second.col)

    val x = rowsEmptySpace.count { it in this.first.row..this.second.row || it in this.second.row..this.first.row }
    val y = colEmptySpace.count { it in this.first.col..this.second.col || it in this.second.col..this.first.col }

    // Example when the expansion = 5, we have 4 steps between row or column to add
    val stepsByEmptySpace = factorExpansion - 1

    return stepLine + stepCol + x * stepsByEmptySpace + y * stepsByEmptySpace
}