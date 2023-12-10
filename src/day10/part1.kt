package day10

import readInput

fun main() {
    var startedPosition: Position? = null

    val diagram: List<List<Pipe?>> = readInput("Day10_part1").mapIndexed { idx, line ->

        val column = line.indexOf("S")
        if (column != -1) startedPosition = Position(idx, column)

        line.map { it.toPipe() }
    }

    val maxRows = diagram.size - 1
    val maxColumns = diagram[0].size - 1

    val loops: MutableList<MutableList<Position>> = mutableListOf()

    for (direction in Direction.entries) {

        var currentDirection = direction
        var currentPosition = startedPosition!!

        val loop = mutableListOf<Position>()
        loop_tiles@ while (true) {
            val nextPosition = currentPosition.nextPosition(currentDirection)
            loop.add(nextPosition)

            if (!(nextPosition.row in 0..maxRows && nextPosition.column in 0..maxColumns)) break@loop_tiles

            val nextPipe = diagram[nextPosition.row][nextPosition.column] ?: break@loop_tiles

            if (currentDirection canBeConnected nextPipe.symbol) {
                currentPosition = nextPosition

                val directionIn = when(currentDirection) {
                    Direction.NORTH -> Direction.SOUTH
                    Direction.SOUTH -> Direction.NORTH
                    Direction.WEST -> Direction.EAST
                    Direction.EAST -> Direction.WEST
                }

                currentDirection = if (directionIn == nextPipe.connectedBy.first) nextPipe.connectedBy.second else nextPipe.connectedBy.first
            } else {
                break@loop_tiles
            }
        }
        loops += loop
    }

    loops.filter { it.last() == startedPosition }.let {
        it[0].zip(it[1]).indexOfFirst { zip -> zip.first == zip.second }.also(::println)
    }
}

fun Position.nextPosition(to: Direction) = when(to) {
    Direction.NORTH -> Position(row - 1, column)
    Direction.SOUTH -> Position(row + 1, column)
    Direction.EAST -> Position(row, column + 1)
    Direction.WEST -> Position(row, column - 1)
}

infix fun Direction.canBeConnected(symbol: Char) = when(this) {
    Direction.SOUTH -> "|LJ".any { it == symbol }
    Direction.NORTH -> "|F7".any { it == symbol }
    Direction.EAST -> "-J7".any { it == symbol }
    Direction.WEST -> "-FL".any { it == symbol }
}

fun Char.toPipe() = when {
    this == '|' -> Pipe(this, Direction.NORTH to Direction.SOUTH)
    this == '-' -> Pipe(this, Direction.WEST to Direction.EAST)
    this == 'L' -> Pipe(this, Direction.EAST to Direction.NORTH)
    this == 'J' -> Pipe(this, Direction.WEST to Direction.NORTH)
    this == '7' -> Pipe(this, Direction.WEST to Direction.SOUTH)
    this == 'F' -> Pipe(this, Direction.EAST to Direction.SOUTH)
    else -> null
}

enum class Direction { NORTH, SOUTH, EAST, WEST }
data class Position(val row: Int, val column: Int)
data class Pipe(val symbol: Char, val connectedBy: Pair<Direction, Direction>)