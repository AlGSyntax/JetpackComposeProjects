package com.algsyntax.todojetpackcompose.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.algsyntax.todojetpackcompose.ui.components.TaskItem
import com.algsyntax.todojetpackcompose.viewmodel.TaskViewModel

@Composable
fun TaskListScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())

    LazyColumn {
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

