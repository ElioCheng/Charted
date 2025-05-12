package shared.entities

// Command pattern
// represents all valid commands that can be issued by the user
// any functionality for a given command should be contained in that class
interface Command {
    fun execute(items: MutableList<Task>)
}

class AddCommand(val content: String) : Command {
    override fun execute(items: MutableList<Task>) {
        items.addTask(Task(content = content))
    }
}

class DelCommand(val index: String) : Command {
    override fun execute(items: MutableList<Task>) {
        items.removeTask(items[index.toInt()])
    }
}

class ListCommand() : Command {
    override fun execute(items: MutableList<Task>) {
        items.forEach { println("[${it.index}] ${it.content}") }
    }
}