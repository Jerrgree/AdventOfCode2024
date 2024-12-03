@file:Suppress("unused")

fun main() {

    val input = readInput("Day03")
    println("Day 03 Part 1: ${day03Pt1(input)}")
    println("Day 03 Part 2: ${day03Pt2(input)}")
}

fun day03Pt1(input: List<String>): Int {
    var sum = 0
    for (line in input) {
        sum += processMulInstructions(line)
    }

    return sum
}

fun day03Pt2(input: List<String>): Int {
    var enabled = true
    var sum = 0
    for (line in input) {
        val result = processEnhancedMulInstructions(line, enabled)

        sum += result.first
        enabled = result.second
    }

    return sum
}

fun processMulInstructions(input: String): Int {
    val matcher = "mul\\(\\d+,\\d+\\)".toRegex()

    val matches = matcher.findAll(input)
    var result = 0
    for (match in matches) {
        result += executeMult(match.value)
    }

    return result
}

fun processEnhancedMulInstructions(input: String, initialEnable: Boolean): Pair<Int, Boolean> {
    var enable = initialEnable

    val matcher = "mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)".toRegex()

    val matches = matcher.findAll(input)
    var result = 0
    for (match in matches) {
        var value = match.value
        if (value == "do()")
            enable = true
        else if (value == "don't()")
            enable = false
        else {
            if (!enable)
                continue

            result += executeMult(value)
        }

    }

    return Pair(result, enable)
}

fun executeMult(input: String): Int {
    val digitMatcher = "\\d+".toRegex()
    val digits = digitMatcher.findAll(input)

    val first = digits.first().value
    val last = digits.last().value
    return first.toInt() * last.toInt()
}