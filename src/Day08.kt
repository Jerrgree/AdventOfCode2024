@file:Suppress("unused")

import kotlin.math.abs


fun main() {
    val input = readInput("Day08")
    println("Day 08 Part 1: ${day08Pt1(input)}")
    println("Day 08 Part 2: ${day08Pt2(input)}")
}

fun day08Pt1(input: List<String>): Int {
    val height = input.count() - 1
    val width = input[0].count() - 1
    val antennas = processMap(input)

    val antiNodes = locateAntiNodes(antennas, width, height, ::processFrequency)

    return antiNodes.count()
}

fun day08Pt2(input: List<String>): Int {
    val height = input.count() - 1
    val width = input[0].count() - 1
    val antennas = processMap(input)

    val antiNodes = locateAntiNodes(antennas, width, height, ::processFrequencyPt2)

    return antiNodes.count()
}

private fun processMap(input: List<String>): Map<Char, List<Pair<Int, Int>>> {
    var map = HashMap<Char, MutableList<Pair<Int, Int>>>()
    for (i in 0..<input.count()) {
        for (j in 0..<input[0].count()) {
            val symbol = input[i][j]
            if (symbol == '.')
                continue

            val point = Pair(j, i)

            //map[symbol]?.add(point) ?: map[symbol] = mutableListOf(point)
            if (map.contains(symbol)) {
                map[symbol]!!.add(point)
            } else {
                map[symbol] = mutableListOf(point)
            }
        }
    }

    return map
}

private fun locateAntiNodes(
    antennas: Map<Char, List<Pair<Int, Int>>>,
    width: Int,
    height: Int,
    processFrequencyCallback: (Char, List<Pair<Int, Int>>, Int, Int) -> HashSet<Pair<Int, Int>>
): HashSet<Pair<Int, Int>> {
    val antiNodes = HashSet<Pair<Int, Int>>()
    for (frequency in antennas) {
        antiNodes.addAll(processFrequencyCallback(frequency.key, frequency.value, width, height))
    }

    return antiNodes
}

private fun processFrequency(
    frequency: Char,
    antennas: List<Pair<Int, Int>>,
    width: Int,
    height: Int
): HashSet<Pair<Int, Int>> {
    val antiNodes = HashSet<Pair<Int, Int>>()
    for (i in 0..<antennas.count()) {
        val primeNode = antennas[i]
        for (j in (i + 1)..<antennas.count()) {
            val secondNode = antennas[j]
            val rise = abs(primeNode.second - secondNode.second)
            val run = primeNode.first - secondNode.first

            var bottomNode = Pair(secondNode.first - run, secondNode.second + rise)
            var topNode = Pair(primeNode.first + run, primeNode.second - rise)

            if (bottomNode.isValid(width, height))
                antiNodes.add(bottomNode)

            if (topNode.isValid(width, height))
                antiNodes.add(topNode)
        }
    }
    return antiNodes
}

private fun processFrequencyPt2(
    frequency: Char,
    antennas: List<Pair<Int, Int>>,
    width: Int,
    height: Int
): HashSet<Pair<Int, Int>> {
    val antiNodes = HashSet<Pair<Int, Int>>()
    for (i in 0..<antennas.count()) {
        val primeNode = antennas[i]
        for (j in (i + 1)..<antennas.count()) {
            val secondNode = antennas[j]
            antiNodes.add(primeNode)
            antiNodes.add(secondNode)
            val rise = abs(primeNode.second - secondNode.second)
            val run = primeNode.first - secondNode.first

            // Traverse Down
            var count = 1
            while (true) {
                val currentRun = run * count
                val currentRise = rise * count

                val nextNode = Pair(primeNode.first - currentRun, primeNode.second + currentRise)

                if (!nextNode.isValid(width, height))
                    break

                antiNodes.add(nextNode)

                count++
            }

            // Reset
            count = 1

            // Traverse Down
            while (true) {
                val currentRun = run * count
                val currentRise = rise * count

                val nextNode = Pair(primeNode.first + currentRun, primeNode.second - currentRise)

                if (!nextNode.isValid(width, height))
                    break

                antiNodes.add(nextNode)

                count++
            }
        }
    }
    return antiNodes
}

private fun Pair<Int, Int>.isValid(width: Int, height: Int): Boolean =
    this.first <= width && this.first >= 0
            && this.second <= height && this.second >= 0