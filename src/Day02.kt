@file:Suppress("unused")

fun main() {
    val input = readInput("Day02")
    processInputEnhanced(input)
    println("Day 02 Part 1: ${day02Pt1(input)}")
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

fun List<Int>.isIncreasing(): Boolean {
    for (i in 1..<this.count()) {
        val curr = this[i]
        val prev = this[i - 1]
        if (curr <= prev)
            return false
        if (curr - prev > 3)
            return false
    }
    return true
}

fun List<Int>.isDecreasing(): Boolean {
    for (i in 1..<this.count()) {
        val curr = this[i]
        val prev = this[i - 1]
        if (curr >= prev)
            return false
        if (prev - curr > 3)
            return false
    }
    return true
}