import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("resources/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun String.splitBySpace() = this.split("\\s+".toRegex())

fun List<String>.toInt() = this.map{ it.toInt() }

fun <T: Any> MutableMap<T, Int>.increment(key: T) {
    if (key in this.keys)
        this[key] = (this[key]!! + 1)
    else
        this[key] = 1
}