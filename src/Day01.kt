import kotlin.math.abs

fun main() {
    val input = readInput("..\\resources\\Day01")
    processInput(input)
}

fun processInput(input: List<String>) {
    val left = arrayListOf<Int>()
    val right = arrayListOf<Int>()
    var sum = 0

    for (line in input) {
        val values = line.split(' ')

        left.add(values[0].toInt())
        right.add(values[3].toInt())
    }

    left.sort()
    right.sort()

    for (i in 0..<left.count()) {
        sum += abs(left[i] - right[i])
    }

    sum.println()
}