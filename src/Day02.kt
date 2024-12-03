@file:Suppress("unused")

fun main() {

    val input = readInput("Day02")
    println("Day 02 Part 1: ${day02Pt1(input)}")
    println("Day 02 Part 2: ${day02Pt2(input)}")
}

fun day02Pt1(input: List<String>): Int {
    var count = 0
    for(line in input) {
        val values = line
            .splitBySpace()
            .toInt()

        if (values.isIncreasing() || values.isDecreasing())
            count++
    }
    return count
}

fun day02Pt2(input: List<String>): Int {
    var count = 0
    for(line in input) {
        val values = line
            .splitBySpace()
            .toInt()

        if (bruteForceEntry(values))
            count++
    }
    return count
}

fun bruteForceEntry(input: List<Int>): Boolean {
    if (input.isIncreasing() || input.isDecreasing())
        return true
    for (i in 0..input.count()) {
        var newInput = input.filterIndexed { index, _ ->
            index != i
        }

        if (newInput.isIncreasing() || newInput.isDecreasing())
            return true
    }

    return false
}

fun List<Int>.isIncreasing(): Boolean = processList(this) { curr: Int, next: Int ->
    curr >= next || next - curr > 3
}

fun List<Int>.isDecreasing(): Boolean = processList(this) { curr: Int, next: Int ->
    curr <= next || curr - next > 3
}

fun processList(list: List<Int>, isInvalid: (curr: Int, next: Int)->Boolean): Boolean {
    val end = list.count() - 2
    for (i in 0..end) {
        val curr = list[i]
        val next = list[i + 1]

        if (isInvalid(curr, next))
            return false
    }
    return true
}