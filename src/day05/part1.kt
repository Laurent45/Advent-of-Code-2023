package day05

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {

    val expectedPart1 = 35L

    val input = Path("src/main/resources/Day05_part1_test.txt").readLines()
    val seeds = input[0].substringAfter(": ").split(" ").map(String::toLong).toLongArray()

    input.drop(2).apply {
        var idx = 0
        while (idx < this.size) {
            if (this[idx].endsWith("map:")) {
                idx++
                // Source range to Destination range
                // 50 98 2 => source = 98..99 | destination = 50..51
                val categories = mutableMapOf<LongRange, LongRange>()

                while (idx < this.size && this[idx].isNotEmpty()) {
                    this[idx].split(" ").map(String::toLong)
                        .apply { categories[this[1]..<this[1] + this[2]] = this[0]..<this[0] + this[2] }
                    idx++
                }
                for (i in seeds.indices) {
                    categories.keys
                        .find { seeds[i] in it }
                        ?.apply {
                            seeds[i] = categories[this]!!.first + (seeds[i] - this.first)
                        }
                }
            }
            idx++
        }
    }

    seeds.min().apply {
        check(this == expectedPart1)
        println(this)
    }
}