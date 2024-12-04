@file:Suppress("unused")

fun main() {

    val input = readInput("Day04")
    println("Day 04 Part 1: ${day04Pt1(input)}")
    println("Day 04 Part 2: ${day04Pt2(input)}")
}

fun day04Pt1(input: List<String>): Int {
    val height = input.count() - 1
    val width = input[0].length - 1
    var count = 0
    for (i in 0..height) {
        for (j in 0..width) {
            if (input[i][j] != 'X')
                continue

            count += traverseGridSimple(input, j, i, height, width)
        }
    }

    return count
}

fun day04Pt2(input: List<String>): Int {
    val height = input.count() - 1
    val width = input[0].length - 1
    var count = 0
    for (i in 0..height) {
        for (j in 0..width) {
            if (input[i][j] != 'A')
                continue

            count += xMas(input, j, i, height, width)
        }
    }

    return count
}

private fun traverseGridSimple(input: List<String>, x: Int, y: Int, width: Int, height: Int): Int {
    var count = 0
    // up
    if (y - 3 >= 0 &&
        (input[y - 1][x] == 'M' && input[y - 2][x] == 'A' && input[y - 3][x] == 'S')
    )
        count++

    if (y + 3 <= height &&
        (input[y + 1][x] == 'M' && input[y + 2][x] == 'A' && input[y + 3][x] == 'S')
    )
        count++

    // left
    if (x - 3 >= 0 &&
        (input[y][x - 1] == 'M' && input[y][x - 2] == 'A' && input[y][x - 3] == 'S')
    )
        count++

    // right
    if (x + 3 <= width &&
        (input[y][x + 1] == 'M' && input[y][x + 2] == 'A' && input[y][x + 3] == 'S')
    )
        count++

    // ldiag up
    if (y - 3 >= 0 && x - 3 >= 0 &&
        (input[y - 1][x - 1] == 'M' && input[y - 2][x - 2] == 'A' && input[y - 3][x - 3] == 'S')
    )
        count++

    // ldiag down
    if (y + 3 <= height && x - 3 >= 0 &&
        (input[y + 1][x - 1] == 'M' && input[y + 2][x - 2] == 'A' && input[y + 3][x - 3] == 'S')
    )
        count++

    // rdiag up
    if (y - 3 >= 0 && x + 3 <= width &&
        (input[y - 1][x + 1] == 'M' && input[y - 2][x + 2] == 'A' && input[y - 3][x + 3] == 'S')
    )
        count++

    // rdiag down
    if (y + 3 <= height && x + 3 <= width &&
        (input[y + 1][x + 1] == 'M' && input[y + 2][x + 2] == 'A' && input[y + 3][x + 3] == 'S')
    )
        count++

    return count
}

private fun xMas(input: List<String>, x: Int, y: Int, width: Int, height: Int): Int {
    if (y - 1 < 0 || y + 1 > height ||
        x - 1 < 0 || x + 1 > width
    )
        return 0

    if ((input[y - 1][x - 1] == 'M' && input[y + 1][x + 1] == 'S'
                || input[y - 1][x - 1] == 'S' && input[y + 1][x + 1] == 'M') &&
        (input[y + 1][x - 1] == 'M' && input[y - 1][x + 1] == 'S'
                || input[y + 1][x - 1] == 'S' && input[y - 1][x + 1] == 'M')
    )
        return 1
    return 0
}