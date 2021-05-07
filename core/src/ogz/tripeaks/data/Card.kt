package ogz.tripeaks.data

import ogz.tripeaks.Util
import java.util.*
import kotlin.math.abs

class Card {
    var suit: Suit = Suit.Club
        get() = field
        private set
    var rank: Rank = Rank.Ace
        get() = field
        private set
    var isOpen: Boolean = false
    private var src: Source = Source.Stack

    val source get() = src

    fun set(suit: Suit, rank: Rank, source: Source, open: Boolean = false): Card {
        this.suit = suit
        this.rank = rank
        this.isOpen = open
        src = source
        return this
    }

    fun areConsecutive(other: Card): Boolean {
        val distance = abs(rank.ordinal - other.rank.ordinal)
        return distance == 1 || distance == 12
    }

    override fun toString(): String = "$rank of ${suit}s"

    fun write(): String {
        val index = (src as? Source.Cell)?.let { Util.getIndex(it) } ?: -1
        val open = if (isOpen) 1 else 0
        return "${suit.name} ${rank.name} $open $index"
    }

    fun read(text: String): Card {
        val parts = text.split(' ')
        suit = Suit.valueOf(parts[0])
        rank = Rank.valueOf(parts[1])
        isOpen = parts[2] == "1"
        val index = parts[3].toInt()
        src = if (index == -1) Source.Stack else Source.Cell(Util.getColumn(index), Util.getRow(index))
        return this
    }

    override fun equals(other: Any?): Boolean = other is Card && (other.rank == rank && other.suit == suit)

    override fun hashCode(): Int = Pair(suit, rank).hashCode()
}

