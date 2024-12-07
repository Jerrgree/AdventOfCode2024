@file:Suppress("unused")

import kotlin.math.max
import kotlin.math.min


fun main() {

    val input = readInput("Day06")
//    val input = listOf("....#.....",
//            ".........#",
//            "..........",
//            "..#.......",
//            ".......#..",
//            "..........",
//            ".#..^.....",
//            "........#.",
//            "#.........",
//            "......#...")
    println("Day 06 Part 1: ${day06Pt1(input)}")
    //println("Day 06 Part 2: ${day06Pt2(input)}")
}

fun day06Pt1(input: List<String>): Int {
    val height = input.count() - 1
    val width = input[0].length - 1
    var direction = Direction.UP

    var visitedPoints = hashSetOf<Pair<Int, Int>>()
    var current = locateStart(input)
    val obstacleLocations = getObstacles(input)


    while (true) {
        val start = current
        current = traverseGrid(obstacleLocations, current, direction, width, height)

        visitedPoints.addAll(calculateVisitedPoints(start, current, direction))

        if (current.first == 0 || current.first == width || current.second == 0 || current.second == height)
            break

        direction = direction.rotate()

        //printMap(current, direction, width, height, visitedPoints, obstacleLocations)
    }
    //printMap(current, direction, width, height, visitedPoints, obstacleLocations)
    return visitedPoints.count()
}

private fun locateStart(input: List<String>): Pair<Int, Int> {
    for (i in 0..(input.count() - 1)) {
        for (j in 0..(input[i].count() - 1)) {
            if (input[i][j] == '^')
                return Pair(j, i)
        }
    }
    error("Guard not found")
}

private fun getObstacles(input: List<String>): List<Pair<Int, Int>> {
    val height = input.count() - 1
    val width = input[0].length - 1
    var obstacleLocations = mutableListOf<Pair<Int, Int>>()

    for (i in 0..height) {
        for (j in 0..width) {
            if (input[i][j] == '#')
                obstacleLocations.add(Pair(j, i))
        }
    }

    return obstacleLocations
}

private fun traverseGrid(
    obstacles: List<Pair<Int, Int>>,
    currentLocation: Pair<Int, Int>,
    direction: Direction,
    width: Int,
    height: Int
): Pair<Int, Int> {
    val x = currentLocation.first
    val y = currentLocation.second

    if (y == 80) {
        println("here")
    }

    return when (direction) {
        Direction.UP -> {
            val workingSet = obstacles.filter { it.first == x }.sortedByDescending { it.second }
            val nextObstacle = workingSet.firstOrNull { it.second < y }

            return if (nextObstacle == null)
                Pair(x, 0)
            else
                Pair(x, nextObstacle.second + 1)
        }

        Direction.DOWN -> {
            val workingSet = obstacles.filter { it.first == x }.sortedBy { it.second }
            val nextObstacle = workingSet.firstOrNull { it.first == x && it.second > y }

            return if (nextObstacle == null)
                Pair(x, height)
            else
                Pair(x, nextObstacle.second - 1)
        }

        Direction.RIGHT -> {
            val workingSet = obstacles.filter { it.second == y }.sortedBy { it.first }
            val nextObstacle = workingSet.firstOrNull { it.first > x }

            return if (nextObstacle == null)
                Pair(width, y)
            else
                Pair(nextObstacle.first - 1, y)
        }

        Direction.LEFT -> {
            val workingSet = obstacles.filter { it.second == y }.sortedByDescending { it.first }
            val nextObstacle = workingSet.firstOrNull { it.first < x }

            return if (nextObstacle == null)
                Pair(0, y)
            else
                Pair(nextObstacle.first + 1, y)
        }
    }
}

private fun calculateVisitedPoints(
    start: Pair<Int, Int>,
    end: Pair<Int, Int>,
    direction: Direction
): List<Pair<Int, Int>> {
    return if (direction == Direction.UP || direction == Direction.DOWN) {
        val x = start.first
        val min = min(start.second, end.second)
        val max = max(start.second, end.second)

        (min..max).map { y -> Pair(x, y) }
    } else {
        val y = start.second
        val min = min(start.first, end.first)
        val max = max(start.first, end.first)

        (min..max).map { x -> Pair(x, y) }
    }
}

private enum class Direction {
    UP, DOWN, LEFT, RIGHT,
}

private fun Direction.rotate(): Direction = when (this) {
    Direction.UP -> Direction.RIGHT
    Direction.RIGHT -> Direction.DOWN
    Direction.DOWN -> Direction.LEFT
    Direction.LEFT -> Direction.UP
}

private fun printMap(
    current: Pair<Int, Int>,
    direction: Direction,
    width: Int,
    height: Int,
    visitedPoints: HashSet<Pair<Int, Int>>,
    obstacles: List<Pair<Int, Int>>
) {
    for (i in 0..height) {
        for (j in 0..width) {
            if (current.first == j && current.second == i) {
                when (direction) {
                    Direction.UP -> print('^')
                    Direction.DOWN -> print('v')
                    Direction.LEFT -> print('<')
                    Direction.RIGHT -> print('>')
                }
            } else if (obstacles.contains(Pair(j, i))) {
                print('#')
            } else if (visitedPoints.contains(Pair(j, i))) {
                print('X')
            } else {
                print('.')
            }
        }
        print('\n')
    }

    print("\n\n\n\n")
}