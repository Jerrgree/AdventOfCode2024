@file:Suppress("unused")

fun main() {
    val input = readInput("Day07")
    println("Day 07 Part 1: ${day07Pt1(input)}")
    println("Day 07 Part 2: ${day07Pt2(input)}")
}

fun day07Pt1(input: List<String>): Long {
    var sum = 0L
    for (line in input) {
        sum += processLine(line, ::processLinePt1Recursive)
    }
    return sum
}

fun day07Pt2(input: List<String>): Long {
    var sum = 0L
    for (line in input) {
        sum += processLine(line, ::processLinePt2Recursive)
    }
    return sum
}

private fun processLine(line: String, callback: (Long, Long, List<Long>) -> Boolean): Long {
    val (score, values) = parseLine(line)
    val start = values[0]
    return if (callback(score, start, values.drop(1))) score else 0
}

private fun processLinePt1Recursive(score: Long, accumulate: Long, values: List<Long>): Boolean {
    if (values.isEmpty())
        return score == accumulate

    // Check both branches, adding and then multiplying
    return processLinePt1Recursive(score, accumulate + values.first(), values.drop(1)) || processLinePt1Recursive(
        score,
        accumulate * values.first(),
        values.drop(1)
    )
}

private fun processLinePt2Recursive(score: Long, accumulate: Long, values: List<Long>): Boolean {
    if (values.isEmpty())
        return score == accumulate

    val current = values.first()
    // Check all branches, adding and then multiplying and then concatenating
    return processLinePt2Recursive(score, accumulate + current, values.drop(1))
            || processLinePt2Recursive(score, accumulate * current, values.drop(1))
            || processLinePt2Recursive(score, accumulate concat current, values.drop(1))
}

private fun parseLine(line: String): Pair<Long, List<Long>> {
    val split = line.split(':')
    val score = split[0].toLong()
    val values = split[1].trim()
        .splitBySpace()
        .toLong()

    return Pair(score, values)
}