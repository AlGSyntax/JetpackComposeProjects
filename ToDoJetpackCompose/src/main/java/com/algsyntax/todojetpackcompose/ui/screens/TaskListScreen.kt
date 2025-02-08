package com.algsyntax.todojetpackcompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.algsyntax.jetpackcomposetodo.data.model.Task
import com.algsyntax.todojetpackcompose.ui.components.AddTaskDialog
import com.algsyntax.todojetpackcompose.ui.components.TaskItem
import com.algsyntax.todojetpackcompose.viewmodel.TaskViewModel

/**
 * A composable function that displays a list of tasks in a "terminal-like" style.
 *
 * The UI is designed to mimic the appearance of a basic terminal, using a monochromatic
 * color scheme and a monospace font to give it a primitive and fundamental feel.
 * Tasks can be added and deleted, and the UI is updated dynamically.
 *
 * @param modifier A Modifier to apply layout adjustments like padding.
 * @param viewModel The ViewModel responsible for managing task states and actions.
 * @param exampletasks A list of sample tasks to display in the preview.
 */
@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,// Modifier to apply layout adjustments.
    viewModel: TaskViewModel = viewModel(),// Initialize the ViewModel
    exampletasks: List<Task> = emptyList()// an empty list
) {
    // Collect the list of tasks from the ViewModel. If no tasks are provided, use the example list.
    val tasks = exampletasks.ifEmpty { viewModel.allTasks.collectAsState(initial = emptyList()).value }

    // State to control whether the dialog for adding tasks is shown or not.
    var showDialog by remember { mutableStateOf(false) }

    // SnackbarHostState to manage the Snackbar messages that appear at the bottom of the screen.
    val snackbarHostState = remember { SnackbarHostState() }

    // Holds the current message to be displayed in the Snackbar.
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    // Composable to show a dialog for adding a new task when needed.
    AddTaskDialog(
        showDialog = showDialog, // Controls visibility of the dialog.
        onDismiss = { showDialog = false }, // Dismiss the dialog when no longer needed.

        // Adds the new task when confirmed, and shows a success message.
        onAddTask = { title, description ->
            viewModel.addTask(title, description) // Adds the task through the ViewModel.
            showDialog = false // Hide the dialog after the task is added.
            snackbarMessage = "Task added successfully" // Set the success message for the Snackbar.
        }
    )

    // Displays the Snackbar whenever snackbarMessage is set.
    snackbarMessage?.let { message ->
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(message) // Show the Snackbar with the current message.
            snackbarMessage = null // Reset the message after showing it.
        }
    }

    // Scaffold provides a basic layout structure and handles the SnackbarHost.
    Scaffold(
        modifier = modifier.fillMaxSize(), // The UI fills the available space.
        snackbarHost = { SnackbarHost(snackbarHostState) } // Handles showing the Snackbar messages.
    ) { innerPadding ->
        // Box is used to stack elements and apply padding inside the Scaffold.
        Box(
            modifier = Modifier
                .padding(innerPadding) // Padding from Scaffold to avoid overlap.
                .background(Color.Black) // Set the background to black for a terminal-like look.
        ) {
            // LazyColumn is a vertically scrolling list that lazily loads items as they are needed.
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth() // The list fills the entire width of the screen.
                    .padding(bottom = 60.dp) // Padding at the bottom to avoid overlapping the row.
                    .fillMaxSize() // Ensure the LazyColumn takes the full screen width and height
            ) {
                // First item in the list: A title and a button to add a task.
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth() // The row takes the full width.
                            .padding(16.dp), // Padding around the row for spacing.
                        horizontalArrangement = Arrangement.SpaceBetween // Items in the row are spaced out.
                    ) {
                        // Text showing the title of the task list.
                        BasicText(
                            text = "My Task List",
                            style = TextStyle(
                            fontSize = 24.sp, // Large font size for the title.
                            color = Color.Green, // Green text for the terminal feel.
                            fontFamily = FontFamily.Monospace
                            )// Monospace font to mimic a terminal.
                        )
                        // Box styled as a button for adding a new task.
                        Box(
                            modifier = Modifier
                                .border(1.dp, Color.Green) // Green border around the box to mimic a button.
                                .padding(8.dp) // Padding inside the box to make the text more readable.
                                .clickable { showDialog = true } // Show the dialog when clicked.
                        ) {
                            // Text inside the button, styled as green monospace text.
                            BasicText(
                                text = "Add Task",
                                style = TextStyle(
                                fontSize = 16.sp, // Smaller font size for the button text.
                                color = Color.Green, // Green text.
                                fontFamily = FontFamily.Monospace, // Monospace font for consistency.
                                textAlign = TextAlign.Center
                                )// Text centered inside the button.
                            )
                        }
                    }
                }

                // Loop over the list of tasks and display each one in the LazyColumn.
                items(tasks.size) { index ->
                    val task = tasks[index]
                    TaskItem(
                        task = task, // Task information.
                        onTaskChecked = { updatedTask ->
                            // Update the task completion status in the ViewModel.
                            viewModel.completeTask(updatedTask, iscompleted = updatedTask.isCompleted)
                        },
                        onDeleteTask = { _ ->
                            // Delete the selected task from the ViewModel.
                            viewModel.deleteTask(task)
                            // Set the snackbar message for successful deletion.
                            snackbarMessage = "Task deleted successfully"
                        }
                    )
                }
            }

            // A row at the bottom of the screen to show the task count and a button to clear completed tasks.
            Row(
                modifier = Modifier
                    .fillMaxWidth() // The row takes the full width of the screen.
                    .align(Alignment.BottomCenter) // Align the row at the bottom of the screen.
                    .padding(16.dp), // Padding around the row for spacing.
                horizontalArrangement = Arrangement.SpaceBetween, // Space the row's contents.
                verticalAlignment = Alignment.CenterVertically // Vertically center the items in the row.
            ) {
                // Text showing the total number of tasks.
                BasicText(
                    text = "Total tasks: ${tasks.size}", // Display the total count dynamically.
                    style = TextStyle(
                    fontSize = 16.sp, // Smaller font size for the count.
                    color = Color.Green, // Green text for the terminal theme.
                    fontFamily = FontFamily.Monospace // Monospace font for consistency.
                )
                )

                // Box styled as a button to clear completed tasks.
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Green) // Green border around the box for the button look.
                        .padding(8.dp) // Padding inside the box.
                        .clickable { viewModel.clearCompletedTasks() } // Clear completed tasks when clicked.
                ) {
                    // Text inside the button, styled as green monospace text.
                    BasicText(
                        text = "Clear Completed", // Button label.
                        style = TextStyle(
                        fontSize = 16.sp, // Smaller font size for the button text.
                        color = Color.Green, // Green text.
                        fontFamily = FontFamily.Monospace, // Monospace font for consistency.
                        textAlign = TextAlign.Center // Text centered inside the button.
                    )
                    )
                }
            }
        }
    }
}

/**
 * A preview composable to show the TaskListScreen with sample data.
 * This allows you to see how the UI would look with some example tasks.
 */
@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    // Sample data for the preview.
    val sampleTasks = listOf(
        Task(id = 1, title = "Sample Task 1", description = "Description 1", isCompleted = false),
        Task(id = 2, title = "Sample Task 2", description = "Description 2", isCompleted = true),
        Task(id = 3, title = "Sample Task 3", description = "Description 3", isCompleted = false)
    )

    // Display the TaskListScreen with the sample data.
    TaskListScreen(exampletasks = sampleTasks)
}