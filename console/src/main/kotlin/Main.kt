package console

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.versionOption
import shared.entities.*
import shared.model.Model
import kotlin.system.exitProcess

/**
 * Main.kt
 * Main entry point for our console application.
 */

// hardcode the data file to the user's home directory
private val FILENAME = "${System.getProperty("user.home")}/mm.json"

fun main(args: Array<String>) = mm().versionOption("1.3").main(args)

// subclass for command-line processing
class mm : CliktCommand() {
    private val command by argument(help = "Command to execute (add|del|list)").default("list")
    private val arg by argument(help = "Task contents|ID").default("")

    override fun run() {
        // load previously saved list
        val model = Model(FILENAME)

        // create command to execute
        when (command) {
            "add" -> {
                model.add(Task(content = arg))
                model.list.forEach { println("[${it.index}] ${it.content}") }
            }
            "del" -> model.del(model.list[(arg as Int)-1])
            "list" -> model.list.forEach { println("[${it.index}] ${it.content}") }
            else -> println(model.list)
        }

        // save and exit
        model.save()
        exitProcess(0)
    }
}