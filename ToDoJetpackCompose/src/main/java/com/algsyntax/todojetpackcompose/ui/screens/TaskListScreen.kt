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
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * A composable function that displays a list of tasks in a scrollable column.
 *
 * This function observes the tasks from the provided TaskViewModel using Flow and
 * displays them using the TaskItem composable. It also provides UI for adding new tasks
 * and deleting existing ones. The layout can be modified using the provided Modifier.
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

    // States to manage the dialog visibility and the input fields for the new task.
    var showDialog by remember { mutableStateOf(false) }
    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDescription by remember { mutableStateOf("") }

    // Display a dialog for adding a new task.
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
                        // Add the new task through the ViewModel.
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

    // Box layout to manage the positioning of the task list and the bottom Row.
    Box(modifier = modifier.fillMaxSize()) {
        // LazyColumn provides a vertically scrollable list of tasks.
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 60.dp) // Padding to prevent overlap with the bottom Row
        ) {
            // The first item displays the title and the Add Task button.
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

            // Display each task in the list using TaskItem.
            items(tasks.size) { index ->
                val task = tasks[index]

                TaskItem(
                    task = task,
                    onTaskChecked = { updatedTask ->
                        // Toggles the completion status of the task.
                        viewModel.completeTask(updatedTask)
                    },
                    onDeleteTask = { task ->
                        // Deletes the selected task.
                        viewModel.deleteTask(task)
                    }
                )
            }
        }

        // Row to display the total number of tasks and a button to clear completed tasks.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter) // Aligns the Row at the bottom of the screen
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total tasks: ${tasks.size}",
                fontSize = 16.sp
            )

            // Button to clear all completed tasks.
            Button(onClick = { viewModel.clearCompletedTasks() }) {
                Text(text = "Clear Completed")
            }
        }
    }
}