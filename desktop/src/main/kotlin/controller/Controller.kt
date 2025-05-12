package controller

import shared.model.Model
import desktop.view.ViewEvent
import shared.entities.Task

class Controller(val model: Model) {
    fun invoke(event: ViewEvent, obj: Any) {
        when(event) {
            ViewEvent.Add -> model.add(obj as Task)
            ViewEvent.Del -> model.del(obj as Task)
            ViewEvent.Update -> model.list[(obj as Task).index].content = obj.content
            ViewEvent.Save -> model.save()
            ViewEvent.Exit -> return
        }
    }
}