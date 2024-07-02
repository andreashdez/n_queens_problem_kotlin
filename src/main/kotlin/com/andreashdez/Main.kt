package com.andreashdez

fun main() {
    val geneticAlgorithm = GeneticAlgorithm(8, 40)
    val bestChromosome = geneticAlgorithm.runGeneticAlgorithm()
    val positions = bestChromosome.genes.map { g -> g.position }
    val conflicts = bestChromosome.genes.map { g -> g.conflicts }
    drawBoard(positions, conflicts)
}
