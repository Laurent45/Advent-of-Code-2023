package day10

import println
import readInput


fun main() {

    // Symbol of pipe map to in/out direction
    val pipes = mapOf(
        '|' to Pair(Direction.NORTH, Direction.SOUTH),
        '-' to Pair(Direction.EAST, Direction.WEST),
        'J' to Pair(Direction.NORTH, Direction.WEST),
        'L' to Pair(Direction.NORTH, Direction.EAST),
        'F' to Pair(Direction.SOUTH, Direction.EAST),
        '7' to Pair(Direction.SOUTH, Direction.WEST)
    )

    var startedPosition: Position? = null

    val diagram: List<List<Char>> = readInput("Day10_part1").mapIndexed { idx, line ->

        val column = line.indexOf("S")
        if (column != -1) startedPosition = Position(idx, column)

        line.toList()
    }

    val maxRows = diagram.size - 1
    val maxColumns = diagram[0].size - 1

    Direction.entries.map { startedDirection ->

        var currentPosition = startedPosition!!
        var currentDirection = startedDirection
        val crossPositions = mutableListOf<Position>()

        loop_tiles@ while (true) {
            val nextPosition = currentPosition.nextPosition(currentDirection)

            if (!(nextPosition.row in 0..maxRows && nextPosition.column in 0..maxColumns)) break@loop_tiles

            val nextPipe = diagram[nextPosition.row][nextPosition.column]

            if (nextPipe == '.') break@loop_tiles
            if (nextPipe == 'S') {
                crossPositions.add(nextPosition)
                break@loop_tiles
            }

            val enterBy = currentDirection.reverse()
            val connectedBy = pipes[nextPipe] ?: error("Shouldn't possible")

            currentDirection = if (connectedBy.first == enterBy) connectedBy.second
            else if (connectedBy.second == enterBy) connectedBy.first
            else break@loop_tiles

            crossPositions.add(nextPosition)
            currentPosition = nextPosition
        }

        crossPositions
    }
        .filter { it.isNotEmpty() }
        .first { it.last() == startedPosition }
        .let { it.size / 2 }
        .println()
}

fun Position.nextPosition(to: Direction) = when (to) {
    Direction.NORTH -> Position(row - 1, column)
    Direction.SOUTH -> Position(row + 1, column)
    Direction.EAST -> Position(row, column + 1)
    Direction.WEST -> Position(row, column - 1)
}

fun Direction.reverse() = when (this) {
    Direction.SOUTH -> Direction.NORTH
    Direction.NORTH -> Direction.SOUTH
    Direction.EAST -> Direction.WEST
    Direction.WEST -> Direction.EAST
}

enum class Direction { NORTH, SOUTH, EAST, WEST }
data class Position(val row: Int, val column: Int)