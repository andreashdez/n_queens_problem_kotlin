package com.andreashdez.app

import com.andreashdez.utils.FileUtils
import com.andreashdez.utils.drawBoard

private const val DEFAULT_SIZE = 8;
private const val DEFAULT_INITIAL_POPULATION = 40000;
private const val DEFAULT_MIN_TO_MATE = 10;
private const val DEFAULT_MAX_TO_MATE = 50;
private const val DEFAULT_MAX_EPOCH_COUNT = 50000;

fun main() {
    val fileUtils = FileUtils()
    val boardSize = fileUtils.getConfigPropertyAsInt("board_size", DEFAULT_SIZE)
    val initialPopulation = fileUtils.getConfigPropertyAsInt("initial_population", DEFAULT_INITIAL_POPULATION)
    val minToMate = fileUtils.getConfigPropertyAsInt("min_to_mate", DEFAULT_MIN_TO_MATE)
    val maxToMate = fileUtils.getConfigPropertyAsInt("max_to_mate", DEFAULT_MAX_TO_MATE)
    val maxEpochCount = fileUtils.getConfigPropertyAsInt("max_epoch_count", DEFAULT_MAX_EPOCH_COUNT)
    val geneticAlgorithm = GeneticAlgorithm(boardSize, initialPopulation, minToMate, maxToMate, maxEpochCount)
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
