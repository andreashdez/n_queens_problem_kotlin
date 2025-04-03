package com.andreashdez.app

import com.andreashdez.utils.drawBoard

fun main() {
    val geneticAlgorithm = GeneticAlgorithm(12, 40000)
    geneticAlgorithm.runGeneticAlgorithm()
    val bestChromosome = geneticAlgorithm.getBestChromosome()
    val worstChromosome = geneticAlgorithm.getWorstChromosome()
    val populationSize = geneticAlgorithm.getPopulationSize()
    val positions = bestChromosome.genes.map { g -> g.position }
    val conflicts = bestChromosome.genes.map { g -> g.conflicts }
    println("--------------------------------")
    println("Best  = $bestChromosome")
    println("Worst = $worstChromosome")
    println("Final Population: $populationSize")
    drawBoard(positions, conflicts)
}
