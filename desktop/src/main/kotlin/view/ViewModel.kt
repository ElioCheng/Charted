package desktop.view

import androidx.compose.runtime.mutableStateListOf
import shared.model.Model
import shared.entities.Task
import shared.model.ISubscriber

class ViewModel(private val model: Model) : ISubscriber {
    val list = mutableStateListOf<Task>()

    init {
        model.subscribe(this)
    }

    override fun update() {
        list.clear()
        for (task in model.list) {
            list.add(task)
        }
    }
}