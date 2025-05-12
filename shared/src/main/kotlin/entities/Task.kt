package shared.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Task.kt
 * Our primary data class, used for storing and displaying notes.
 * `index` will eventually be used to control the sort order.
 * `content` represents the task title.
 */

@Serializable
data class Task(
    var index: Int = 0,
    var content: String
)

/**
 * Extend list with functions we will need
 * These replace helper functions that would otherwise modify the list
 */

fun MutableList<Task>.save(filename: String) {
    try {
        val output = Json.encodeToString(this)
        File(filename).writeText(output)
    } catch (exception: Exception) {
        // println("Cannot save file: $filename")
    }
}

fun MutableList<Task>.load(filename: String) {
    try {
        val input = File(filename).readText()
        if (input.isNotEmpty()) {
            this.clear()
            val contents = Json.decodeFromString<MutableList<Task>>(input)
            contents.forEach {
                this.addTask(it)
            }
        }
    } catch (e: Exception) {
        // println("Cannot load file: $filename")
    }
}

fun MutableList<Task>.addTask(element: Task): Boolean {
    this.add(element)
    this.reindex()
    return true
}

fun MutableList<Task>.removeTask (element: Task): Boolean {
    this.remove(element)
    this.reindex()
    return true
}

private fun MutableList<Task>.reindex() {
    var count = 1
    for (task in this) {
        task.index = count++
    }
}