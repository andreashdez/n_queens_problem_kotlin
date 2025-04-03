package com.andreashdez.utils

import kotlin.math.max

fun drawBoard(positions: List<Int>, conflicts: List<Int>) {
    val size = positions.size
    drawTopRow(size)
    for (y in 0 until size) {
        print(BoardElement.BORDER_LEFT.string)
        for (x in 0 until size) {
            val yPosition = positions[x]
            if (yPosition == y) {
                val currentConflicts = conflicts[x]
                print(String.format("%02d", currentConflicts))
            } else {
                print("  ")
            }
            if (x < size - 1) {
                print(BoardElement.BORDER_MIDDLE.string)
            } else {
                println(BoardElement.BORDER_RIGHT.string)
            }
        }
        if (y < size - 1) {
            drawMiddleRow(size)
        }
    }
    drawBottomRow(size)
}

private fun drawTopRow(size: Int) {
    println(
        BoardElement.TOP_LEFT.string
                + BoardElement.TOP_MIDDLE.string.repeat(max(0, (size - 1)))
                + BoardElement.TOP_RIGHT.string
    )
}

private fun drawMiddleRow(size: Int) {
    println(
        BoardElement.MIDDLE_LEFT.string
                + BoardElement.MIDDLE_MIDDLE.string.repeat(max(0, (size - 1)))
                + BoardElement.MIDDLE_RIGHT.string
    )
}

private fun drawBottomRow(size: Int) {
    println(
        BoardElement.BOTTOM_LEFT.string
                + BoardElement.BOTTOM_MIDDLE.string.repeat(max(0, (size - 1)))
                + BoardElement.BOTTOM_RIGHT.string
    )
}
