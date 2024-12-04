@file:Suppress("unused")

fun main() {

    val input = readInput("Day04")
    println("Day 04 Part 1: ${day04Pt1(input)}")
    //println("Day 04 Part 2: ${day04Pt2(input)}")
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

private fun traverseGrid(input: List<String>, x: Int, y: Int, width: Int, height: Int): Int {
    val currentChar = input[y][x]
    val nextExpectedChar = nextChar(currentChar)
    var count = 0

    // top left
    if (y != 0 && x != 0) {
        val ny = y - 1
        val nx = x - 1
        count += checkSquare(input, nx, ny, width, height, nextExpectedChar)
    }

    // top center
    if (y != 0) {
        val ny = y - 1

        count += checkSquare(input, x, ny, width, height, nextExpectedChar)
    }
    // top right
    if (y != 0 && x != width) {
        val ny = y - 1
        val nx = x + 1
        count += checkSquare(input, nx, ny, width, height, nextExpectedChar)
    }

    // left
    if (x != 0) {
        val nx = x - 1
        count += checkSquare(input, nx, y, width, height, nextExpectedChar)
    }

    // right
    if (x != width) {
        val nx = x + 1
        count += checkSquare(input, nx, y, width, height, nextExpectedChar)
    }

    // bot left
    if (y != height && x != 0) {
        val ny = y + 1
        val nx = x - 1
        count += checkSquare(input, nx, ny, width, height, nextExpectedChar)
    }

    // bot center
    if (y != height) {
        val ny = y + 1
        count += checkSquare(input, x, ny, width, height, nextExpectedChar)
    }

    // bot right
    if (y != height && x != width) {
        val ny = y + 1
        val nx = x + 1
        count += checkSquare(input, nx, ny, width, height, nextExpectedChar)
    }

    return count
}

private fun checkSquare(input: List<String>, x: Int, y: Int, width: Int, height: Int, nextExpectedChar: Char): Int {
    val nextChar = input[y][x]
    if (nextChar == nextExpectedChar) {
        return if (nextChar == 'S')
            1
        else
            traverseGrid(input, x, y, width, height)
    }
    return 0
}

private fun nextChar(current: Char) = when (current) {
    'X' -> 'M'
    'M' -> 'A'
    'A' -> 'S'
    else -> {
        error("Invalid Character")
    }
}