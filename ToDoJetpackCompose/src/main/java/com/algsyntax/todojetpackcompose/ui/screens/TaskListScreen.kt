package com.algsyntax.todojetpackcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.algsyntax.jetpackcomposetodo.data.model.Task
import com.algsyntax.todojetpackcompose.ui.components.AddTaskDialog
import com.algsyntax.todojetpackcompose.ui.components.TaskItem
import com.algsyntax.todojetpackcompose.viewmodel.TaskViewModel

/**
 * A composable function that displays a list of tasks in a scrollable column.
 *
 * This function observes the tasks from the provided TaskViewModel using Flow and
 * displays them using the TaskItem composable. It also provides UI for adding new tasks,
 * deleting existing ones, and displaying feedback through Snackbars. The layout can be
 * modified using the provided Modifier.
 *
 * @param modifier A Modifier for applying padding or other layout adjustments. Defaults to Modifier.
 * @param viewModel The ViewModel that provides the list of tasks and manages their state.
 */
@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = viewModel(),
            exampletasks: List<Task> = emptyList()
) {
    // Observes the tasks from the ViewModel as a Flow and collects them as a State.
    val tasks by viewModel.allTasks.collectAsState(initial = emptyList())

    // States to manage the dialog visibility and the input fields for the new task.
    var showDialog by remember { mutableStateOf(false) }


    // Snackbar host state to show feedback messages.
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    // Call the AddTaskDialog composable to show a dialog for adding a new task.
// The dialog is displayed based on the 'showDialog' state.
    AddTaskDialog(
        showDialog = showDialog, // Passes the current state of whether the dialog should be visible or not.
        onDismiss = { showDialog = false }, // Callback to hide the dialog when it is dismissed.

        // Callback invoked when the user confirms adding a new task.
        // The title and description entered by the user are passed to this lambda.
        onAddTask = { title, description ->
            viewModel.addTask(title, description) // Adds the new task to the ViewModel.
            showDialog = false // Hides the dialog after adding the task.
            snackbarMessage = "Task added successfully" // Sets the message for the snackbar to notify the user of the successful addition.
        }
    )
    // Show the snackbar whenever the snackbarMessage changes
    snackbarMessage?.let { message ->
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(message)
            snackbarMessage = null // Reset the message after showing it
        }
    }

    // Scaffold layout to manage the UI structure and provide a SnackbarHost.
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }  // Host for showing Snackbars
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // LazyColumn provides a vertically scrollable list of tasks.
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp)  // Padding to prevent overlap with the bottom Row
            ) {
                // The first item displays the title and the Add Task button.
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "My Task List",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Button(onClick = { showDialog = true }) {
                            Text(text = "Add Task", color = MaterialTheme.colorScheme.onPrimary)
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
                            viewModel.completeTask(updatedTask, iscompleted = updatedTask.isCompleted)
                        },
                        onDeleteTask = { _ ->
                            // Deletes the selected task.
                            viewModel.deleteTask(task)

                            // Set the snackbar message to show a success message
                            snackbarMessage = "Task deleted successfully"
                        }
                    )
                }
            }

            // Row to display the total number of tasks and a button to clear completed tasks.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)  // Aligns the Row at the bottom of the screen
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total tasks: ${tasks.size}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                // Button to clear all completed tasks.
                Button(onClick = { viewModel.clearCompletedTasks() }) {
                    Text(text = "Clear Completed", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}


/**
 * A preview composable that shows the TaskListScreen with some sample tasks.
 */
@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    val sampleTasks = listOf(
        Task(id = 1, title = "Sample Task 1", description = "Description 1", isCompleted = false),
        Task(id = 2, title = "Sample Task 2", description = "Description 2", isCompleted = true),
        Task(id = 3, title = "Sample Task 3", description = "Description 3", isCompleted = false)
    )

    TaskListScreen(exampletasks = sampleTasks)
}