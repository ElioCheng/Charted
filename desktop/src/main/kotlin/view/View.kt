package desktop.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import controller.Controller
import shared.entities.Task

// types of actions our controller class understands
enum class ViewEvent {
    Add,
    Del,
    Update,
    Save,
    Exit
}

@Composable
fun RowColor(selected: Boolean) = if (selected) MaterialTheme.colors.secondary else Color.Transparent

@Composable
fun TaskRow(
    task: Task,
    index: Int,
    selected: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .background(RowColor(index == task.index))
            .clickable(onClick = selected)
    ) {
        Text(text = "[${task.index}] ${task.content}", style = MaterialTheme.typography.body1)
    }
}

@Composable
fun TaskList(
    taskListViewModel: ViewModel,
    taskListController: Controller,
    onExit: () -> Unit
) {
    val viewModel by remember { mutableStateOf(taskListViewModel) }
    val controller by remember { mutableStateOf(taskListController) }
    var selectedIndex by remember { mutableStateOf(1) }

    Window(
        title = "MM Desktop Edition",
        state = WindowState(
            placement = WindowPlacement.Floating,
            position = WindowPosition(Alignment.Center),
            size = DpSize(425.dp, 300.dp),
        ),
        resizable = false,
        onCloseRequest = onExit,
        onKeyEvent = {
            if (it.type == KeyEventType.KeyUp) {
                when (it.key) {
                    Key.J -> selectedIndex = (selectedIndex + 1).coerceAtMost(viewModel.list.size)
                    Key.K -> selectedIndex = (selectedIndex - 1).coerceAtLeast(1)
                }
                // println("Window handler: ${it.key}, index: $selectedIndex")
            }
            false
        }
    ) {
        MaterialTheme {
            MenuItems(onExit, controller, viewModel, selectedIndex)
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (task in viewModel.list) {
                    TaskRow(task, selectedIndex) { selectedIndex = task.index }
                }
            }
        }
    }
}

@Composable
private fun FrameWindowScope.MenuItems(
    onExit: () -> Unit,
    controller: Controller,
    viewModel: ViewModel,
    selectedIndex: Int
) {
    this.MenuBar {
        Menu("File", mnemonic = 'F') {
            Item(
                "Save",
                onClick = { controller.invoke(ViewEvent.Save, selectedIndex) },
                shortcut = KeyShortcut(Key.S, ctrl = true)
            )
            Item(
                "Quit",
                onClick = { onExit() },
                shortcut = KeyShortcut(Key.Q, ctrl = true)
            )
        }
        Menu("Actions", mnemonic = 'T') {
            Item(
                "Add",
                onClick = { controller.invoke(ViewEvent.Add, Task(content = "This is a new task")) },
                shortcut = KeyShortcut(Key.A)
            )
            Item(
                "Edit",
                onClick = { controller.invoke(ViewEvent.Update, viewModel.list[selectedIndex]) },
                shortcut = KeyShortcut(Key.E)
            )
            Item(
                "Delete",
                onClick = { controller.invoke(ViewEvent.Del, viewModel.list[selectedIndex - 1]) },
                shortcut = KeyShortcut(Key.D)
            )
        }
    }
}