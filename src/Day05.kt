@file:Suppress("unused")

import kotlin.math.floor

fun main() {
    val input = readInput("Day05")
    println("Day 05 Part 1: ${day05Pt1(input)}")
    println("Day 05 Part 2: ${day05Pt2(input)}")
}

fun day05Pt1(input: List<String>): Int {
    val (rules, index) = buildRules(input)
    return processUpdates(input, index, rules)
}

fun day05Pt2(input: List<String>): Int {
    val (rules, index) = buildRules(input)
    return fixTheBadOnes(input, index, rules)
}

private fun buildRules(input: List<String>): Pair<List<Pair<Int, Int>>, Int> {
    var rules = mutableListOf<Pair<Int, Int>>()
    var i = 0
    while (true) {
        val next = input[i]
        val values = next.split('|')
        if (values.count() != 2)
            break

        rules.add(Pair(values[0].toInt(), values[1].toInt()))
        i++
    }

    return Pair(rules, i + 1)
}

private fun processUpdates(input: List<String>, start: Int, rules: List<Pair<Int, Int>>): Int {
    val end = input.count() - 1
    var sum = 0
    for (i in start..end) {
        val next = input[i]
        val values = next.split(',')
            .toInt()
        var passes = true
        var seenValues = mutableListOf<Int>()

        for (value in values) {
            val applicableRules = rules.filter { r -> r.first == value }
            if (applicableRules.any { r -> seenValues.any { v -> v == r.second } }) {
                passes = false
                break
            }

            seenValues.add(value)
        }

        if (passes)
            sum += getMiddleNumber(values)
    }

    return sum
}

private fun getMiddleNumber(input: List<Int>): Int {
    var index = floor(input.count() / 2.0).toInt()

    return input[index]
}

private fun fixTheBadOnes(input: List<String>, start: Int, rules: List<Pair<Int, Int>>): Int {
    val end = input.count() - 1
    var badOnes = mutableListOf<List<Int>>()
    for (i in start..end) {
        val next = input[i]
        val values = next.split(',')
            .toInt()
        var passes = true
        var seenValues = mutableListOf<Int>()

        for (value in values) {
            val applicableRules = rules.filter { r -> r.first == value }
            if (applicableRules.any { r -> seenValues.any { v -> v == r.second } }) {
                passes = false
                break
            }

            seenValues.add(value)
        }

        if (!passes)
            badOnes.add(values)
    }

    return processBadOnes(badOnes, rules)
}

private fun processBadOnes(input: List<List<Int>>, rules: List<Pair<Int, Int>>): Int {
    var sum = 0
    val comparer = Comparator { left: Int, right: Int ->

        if (rules.filter { r -> r.first == left }
                .any { r -> r.second == right })
            return@Comparator -1
        return@Comparator 1
    }

    for (value in input) {
        sum += getMiddleNumber(value.sortedWith(comparer).toList())
    }

    return sum
}