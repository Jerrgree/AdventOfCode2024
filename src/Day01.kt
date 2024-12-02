import kotlin.math.abs

fun main() {
    val input = readInput("Day01")
    processInput(input)
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