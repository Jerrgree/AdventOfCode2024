@file:Suppress("unused")

fun main() {

    val input = readInput("Day03")
    println("Day 03 Part 1: ${day03Pt1(input)}")
    //println("Day 03 Part 2: ${day03Pt2(input)}")
}

fun day03Pt1(input: List<String>): Int {
    var sum = 0
    for (line in input) {
        sum += processMulInstructions(line)
    }

    return sum
}

fun processMulInstructions(input: String): Int {
    val matcher = "mul\\(\\d+,\\d+\\)".toRegex()

    val matches = matcher.findAll(input)
    var result = 0
    for (match in matches) {
        val digitMatcher = "\\d+".toRegex()
        val digits = digitMatcher.findAll(match.value)

        val first = digits.first().value
        val last = digits.last().value

        result += (first.toInt() * last.toInt())

    }

    return result
}