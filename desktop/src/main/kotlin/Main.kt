package desktop

import androidx.compose.ui.window.application
import controller.Controller
import shared.model.Model
import desktop.view.TaskList
import desktop.view.ViewModel

fun main() {
    val model = Model("${System.getProperty("user.home")}/mm.json")
    val viewModel = ViewModel(model)
    val controller = Controller(model)

    application {
        TaskList(viewModel, controller, onExit = { exitApplication() })
    }
}