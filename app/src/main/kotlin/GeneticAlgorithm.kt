package com.andreashdez.app

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.math.pow
import kotlin.random.Random

private val logger = KotlinLogging.logger {}

private const val DEFAULT_FITNESS_EXPONENT = 3.0

class GeneticAlgorithm(
    size: Int,
    initialPopulation: Int,
    private val minToMate: Int,
    private val maxToMate: Int,
    private val maxEpochCount: Int
) {

    private val population = (0..initialPopulation).map { chromosome(size) }.toMutableList()

    fun getPopulationSize(): Int {
        return population.size
    }

    fun runGeneticAlgorithm() {
        calculateFitness()
        for (epochCounter in 0 until maxEpochCount) {
            mateRandomChromosomes(minToMate, maxToMate)
            calculateFitness()
            logger.info { "epoch: $epochCounter" }
            logger.info { "best chromosome conflicts sum: " + getBestChromosome().conflictsSum }
            if (getBestChromosome().conflictsSum == 0) {
                return
            }
        }
    }

    fun getBestChromosome(): Chromosome {
        return population.minBy { c -> c.conflictsSum }
    }

    fun getWorstChromosome(): Chromosome {
        return population.maxBy { c -> c.conflictsSum }
    }

    private fun calculateFitness() {
        val mostConflicts = getWorstChromosome().conflictsSum.toDouble()
        val leastConflicts = getBestChromosome().conflictsSum
        val diffConflicts = mostConflicts - leastConflicts
        logger.debug { "calculating fitness [worst='$mostConflicts', best='$leastConflicts', diff='$diffConflicts']" }
        population.forEach { chromosome ->
            chromosome.fitness =
                fitnessFunction(mostConflicts, diffConflicts, chromosome.conflictsSum)
        }
    }

    private fun fitnessFunction(mostConflicts: Double, diffConflicts: Double, totalConflicts: Int): Double {
        return (mostConflicts - totalConflicts).pow(DEFAULT_FITNESS_EXPONENT) /
                diffConflicts.pow(DEFAULT_FITNESS_EXPONENT)
    }

    private fun mateRandomChromosomes(minToMate: Int, maxToMate: Int) {
        val mateAmount = Random.nextInt(minToMate, maxToMate)
        val fitnessSum = population.sumOf { chromosome -> chromosome.fitness }
        logger.debug { "selecting random chromosomes [mateAmount=$mateAmount, fitnessSum=$fitnessSum]" }
        val newChildren: List<Chromosome> = (0..<mateAmount).map { _ ->
            val parentOne: Chromosome = selectRandomChromosome(fitnessSum) ?: getBestChromosome()
            val parentTwo: Chromosome = selectRandomChromosome(fitnessSum) ?: getWorstChromosome()
            this.mateChromosomes(parentOne, parentTwo)
        }.toList()
        population.addAll(newChildren)
    }

    private fun selectRandomChromosome(fitnessSum: Double): Chromosome? {
        val rouletteSpin = Random.nextDouble(fitnessSum)
        var selectionRank = 0.0
        for (chromosome in this.population) {
            selectionRank += chromosome.fitness
            if (selectionRank > rouletteSpin) {
                logger.trace { "selecting chromosome: $chromosome" }
                return chromosome
            }
        }
        return null
    }

    private fun mateChromosomes(parentOne: Chromosome, parentTwo: Chromosome): Chromosome {
        logger.debug { "mate chromosomes" }
        logger.trace { "parentOne: $parentOne" }
        logger.trace { "parentTwo: $parentTwo" }
        val childGenes = pmx(parentOne.genes, parentTwo.genes)
        val child = Chromosome(childGenes)
        logger.debug { "child: $child" }
        return child
    }

    private fun pmx(parentOne: Array<Gene>, parentTwo: Array<Gene>): Array<Gene> {
        val chromosomeSize = parentOne.size
        val chromosomeHalfSize = chromosomeSize / 2
        val pointOne = Random.nextInt(0, chromosomeHalfSize)
        val pointTwo = Random.nextInt(chromosomeHalfSize, chromosomeSize)
        logger.debug { "partially mapped crossover [pointOne=$pointOne, pointTwo=$pointTwo]" }
        val childGenes = MutableList(chromosomeSize) { -1 }
        for (i in 0 until chromosomeSize) {
            if (i in pointOne..<pointTwo) {
                childGenes[i] = parentOne[i].position
            }
        }
        for (i in pointOne until pointTwo) {
            if (!childGenes.contains(parentTwo[i].position)) {
                childGenes[findPosition(i, parentOne, parentTwo, childGenes)] = parentTwo[i].position
            }
        }
        for (i in 0 until chromosomeSize) {
            if (childGenes[i] == -1) {
                childGenes[i] = parentTwo[i].position
            }
        }
        return childGenes.map { p -> Gene(p) }.toTypedArray()
    }

    private fun findPosition(index: Int, parentOne: Array<Gene>, parentTwo: Array<Gene>, child: List<Int>): Int {
        var position = -1
        for (i in parentOne.indices) {
            if (parentTwo[i].position == parentOne[index].position) {
                position = i
                break
            }
        }
        logger.trace { "checking position $position" }
        if (position == -1 || child[position] != -1) {
            return findPosition(position, parentOne, parentTwo, child)
        }
        return position
    }

}
