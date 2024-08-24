package com.algsyntax.todojetpackcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.algsyntax.todojetpackcompose.ui.components.TaskItem
import com.algsyntax.todojetpackcompose.viewmodel.TaskViewModel
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * A composable function that displays a list of tasks in a scrollable column.
 *
 * This function observes the tasks from the provided TaskViewModel
 * using Flow and displays them using the TaskItem composable.
 * It also applies the provided Modifier for padding or other adjustments.
 *
 * @param modifier A Modifier for applying padding or other layout adjustments. Defaults to Modifier.
 * @param viewModel The ViewModel that provides the list of tasks and manages their state.
 */
@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = viewModel()
) {
    // Observes the tasks from the ViewModel as a Flow and collects them as a State.
    val tasks by viewModel.allTasks.collectAsState(initial = emptyList())

    // States to manage the dialog visibility and the input fields for the new task
    var showDialog by remember { mutableStateOf(false) }
    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDescription by remember { mutableStateOf("") }

    // Show a dialog for adding a new task
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Add New Task") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newTaskTitle,
                        onValueChange = { newTaskTitle = it },
                        label = { Text("Title") }
                    )
                    OutlinedTextField(
                        value = newTaskDescription,
                        onValueChange = { newTaskDescription = it },
                        label = { Text("Description") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (newTaskTitle.isNotEmpty() && newTaskDescription.isNotEmpty()) {
                        viewModel.addTask(newTaskTitle, newTaskDescription)
                        showDialog = false
                        newTaskTitle = ""
                        newTaskDescription = ""
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(modifier = modifier.padding(16.dp)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "My Task List", fontSize = 24.sp)
                    Button(onClick = { showDialog = true }) {
                        Text(text = "Add Task")
                    }
                }
            }

            items(tasks.size) { index ->
                val task = tasks[index]
                TaskItem(
                    task = task,
                    onTaskChecked = { updatedTask ->
                        viewModel.completeTask(updatedTask)
                    }
                )
            }
        }

        Text(
            text = "Total tasks: ${tasks.size}",
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}