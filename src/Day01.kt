@file:Suppress("unused")

import kotlin.math.abs

fun main() {
    val input = readInput("Day01")
    processInputEnhanced(input)
}

fun processInput(input: List<String>) {
    val left = arrayListOf<Int>()
    val right = arrayListOf<Int>()
    var sum = 0

    for (line in input) {
        val values = line.splitBySpace().toInt()
        left.add(values[0])
        right.add(values[1])
    }

    left.sort()
    right.sort()

    for (i in 0..<left.count()) {
        sum += abs(left[i] - right[i])
    }

    sum.println()
}

fun processInputEnhanced(input: List<String>) {
    val left = mutableMapOf<Int, Int>()
    val right = mutableMapOf<Int, Int>()
    var sum = 0

    for (line in input) {
        val values = line.splitBySpace().toInt()
        left.increment(values[0])
        right.increment(values[1])
    }

    for (key in left.keys) {
        sum += (key * left[key]!! * (right[key] ?: 0))
    }

    sum.println()
}