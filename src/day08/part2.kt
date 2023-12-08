package day08

import println
import readInput

fun main() {
    val input = readInput("Day08_part2_test")

    // LRLLLRRLL...
    val instructions = input[0].split("").filter { it.isNotBlank() }

    input.drop(2).associate {
        // 11A = (11B, XXX)
        val (id, left, right) = """(\w+) = \((\w+), (\w+)\)""".toRegex().matchEntire(it)?.destructured
            ?: error("Should not happened")

        id to Node(left, right)
    }.let { nodes ->
        val startIds = nodes.keys.filter { it.last() == 'A' }

        val minimumTours = startIds.map { startId ->
            var currentId = startId

            generateSequence(0) { (it + 1) % instructions.size }.takeWhile {
                val instruction = instructions[it]
                val current = nodes[currentId] ?: error("Should not happened")

                currentId = if (instruction == "L") current.left else current.right
                if (currentId.last() == 'Z') println("$startId - $currentId ($it)")
                currentId.last() != 'Z'
            }.count() + 1

        }.toList()

        generateSequence(1) { it + 1 }.takeWhile { factor ->
            val result = minimumTours.map { it * factor }.toSet()
            if (result.size == 1) println(result.first())
            result.size != 1
        }.count()





    }.println()
}