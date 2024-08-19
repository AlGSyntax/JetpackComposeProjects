package com.algsyntax.todojetpackcompose.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())

    LazyColumn(modifier = modifier) {
        items(tasks.size) { index ->
            val task = tasks[index]
            TaskItem(
                task = task,
                onTaskChecked = { isChecked ->
                    viewModel.completeTask(task.id)
                }
            )
        }
    }
}

