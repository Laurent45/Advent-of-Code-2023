package day08

import println
import readInput

fun main() {
    val input = readInput("Day08_part1")

    // LR
    val instructions = input[0].split("").filter { it.isNotBlank() }.also { println(it) }

    input.drop(2).associate {
        // 11A = (11B, XXX)
        val (id, left, right) = """(\w+) = \((\w+), (\w+)\)""".toRegex().matchEntire(it)?.destructured
            ?: error("Should not happened")

        id to Node(left, right)
    }.let { nodes ->
        var currentId = "AAA"

        generateSequence(0) { (it + 1) % instructions.size }.takeWhile {
                val instruction = instructions[it]
                val current = nodes[currentId] ?: error("Should not happened")

                currentId = if (instruction == "L") current.left else current.right
                currentId.last() != 'Z'
            }
            .count() + 1
    }.println()
}

data class Node(val left: String, val right: String)