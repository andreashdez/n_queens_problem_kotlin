package com.andreashdez

import kotlin.math.abs

class Chromosome(val genes: Array<Gene>) {

    init {
        countGeneConflicts()
    }

    val conflictsSum = sumGeneConflicts()
    var fitness = 0.0

    private fun countGeneConflicts() {
        for (xTwo in 0 until genes.size - 1) {
            for (xOne in xTwo + 1 until genes.size) {
                val distance = xOne - xTwo
                val yOne: Int = genes[xOne].position
                val yTwo: Int = genes[xTwo].position
                if (abs((yOne - yTwo)) == distance) {
                    genes[xOne].conflicts++
                    genes[xTwo].conflicts++
                }
            }
        }
    }

    private fun sumGeneConflicts(): Int {
        return genes.sumOf { g -> g.conflicts }
    }

}

fun chromosome(size: Int): Chromosome {
    val genes: Array<Gene> = (0..size)
        .shuffled()
        .map { i -> Gene(i) }
        .toTypedArray()
    return Chromosome(genes)
}
