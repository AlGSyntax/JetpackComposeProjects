package com.algsyntax.todojetpackcompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.algsyntax.todojetpackcompose.ui.components.TaskItem
import com.algsyntax.todojetpackcompose.viewmodel.TaskViewModel

/**
 * A composable function that displays a list of tasks in a scrollable column.
 *
 * This function observes the tasks from the provided TaskViewModel
 * using StateFlow and displays them using the TaskItem composable.
 * It also applies the provided Modifier for padding or other adjustments.
 *
 * @param viewModel The ViewModel that provides the list of tasks and manages their state.
 * @param modifier  A Modifier for applying padding or other layout adjustments. Defaults to Modifier.
 */
@Composable
fun TaskListScreen(viewModel: TaskViewModel, modifier: Modifier = Modifier) {
    // Observes the tasks from the ViewModel as a StateFlow and collects them as a State.
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())

    // LazyColumn is a vertically scrollable list that only composes and lays out visible items.
    LazyColumn(modifier = modifier) {

        // 'item' is used to add a single, non-repeatable item to the LazyColumn.
        item {
            // Row is a horizontal layout that places its children in a row.
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Ensures the Row takes up the full width of the screen.
                    .padding(16.dp), // Adds padding around the Row.
                horizontalArrangement = Arrangement.SpaceBetween // Distributes the space between the children evenly.
            ) {
                // Displays the title of the task list.
                Text(text = "My Task List", fontSize = 24.sp)

                // Button for adding a new task (no action defined yet).
                Button(onClick = { /*TODO: Handle adding a task */ }) {
                    Text(text = "Add Task")
                }
            }
        }

        // 'items' is used to add a list of repeatable items to the LazyColumn.
        items(tasks.size) { index ->
            val task = tasks[index]
            // TaskItem is a Composable that displays an individual task.
            TaskItem(
                task = task,
                onTaskChecked = { isChecked ->
                    // Marks the task as completed when checked.
                    viewModel.completeTask(task.id)
                }
            )
        }
    }
}
