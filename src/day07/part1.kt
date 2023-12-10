package day07

import println
import kotlin.io.path.Path
import kotlin.io.path.readLines

//const val CARD = "AKQJT98765432" //part1
const val CARD = "AKQT98765432J" //part2
fun main() {

    Path("src/main/resources/Day07_part1.txt").readLines()
        .map {
            val (cards, bid) = it.split(" ")
            Hand(cards, bid.toInt())
        }
//        .sortedWith(compareBy({ it.cards.typeOfHand() }, { it })) //part1
        .sortedWith(compareBy({ it.cards.typeOfHandJoker() }, { it })) //part2
        .mapIndexed { idx, hand -> (idx + 1) * hand.bid }
        .sum()
        .println()
}

enum class HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_KIND,
    FULL_HOUSE,
    FOUR_KIND,
    FIVE_KIND
}

fun String.typeOfHand(): HandType = this.groupingBy { it }.eachCount().values.let {
    when (it.size) {
        1 -> HandType.FIVE_KIND
        2 -> if (it.contains(4)) HandType.FOUR_KIND else HandType.FULL_HOUSE
        3 -> if (it.contains(3)) HandType.THREE_KIND else HandType.TWO_PAIR
        4 -> HandType.ONE_PAIR
        5 -> HandType.HIGH_CARD
        else -> error("Should not happened")
    }
}

fun String.typeOfHandJoker(): HandType {
    val cardWithoutJoker = this.filter { it != 'J' }
    val card = if (cardWithoutJoker.isNotBlank()) {
        val label = this.filter { it != 'J' }.groupingBy { it }.eachCount().entries.maxBy { it.value }
        this.replace('J', label.key)
    } else this
    return card.typeOfHand()
}


class Hand(val cards: String, val bid: Int) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int =
        this.cards.zip(other.cards).first { it.first != it.second }
            .let { CARD.indexOf(it.second) - CARD.indexOf(it.first) }
}


