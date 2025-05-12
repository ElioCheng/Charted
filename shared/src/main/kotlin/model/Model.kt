package shared.model

import shared.entities.*

class Model(private val filename: String) : IPublisher() {
    val list = mutableListOf<Task>()

    init {
        list.load(filename)
    }

    fun add(task: Task) {
        list.addTask(task)
        notifySubscribers()
    }

    fun del(task: Task) {
        list.removeTask(task)
        notifySubscribers()
    }

    fun save() {
        list.save(filename)
    }

    override fun toString(): String {
        var s = ""
        list.forEach { s += "[${it.index}] ${it.content}\n" }
        return s
    }
}