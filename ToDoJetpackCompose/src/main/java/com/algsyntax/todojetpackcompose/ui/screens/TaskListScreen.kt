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

    // States to manage the dialog visibility and the input fields for the new task
    var showDialog by remember { mutableStateOf(false) }
    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDescription by remember { mutableStateOf("") }

    // Show a dialog for adding a new task
    // Checks if the dialog should be shown.
    if (showDialog) {
        // AlertDialog is a predefined dialog in Jetpack Compose used for simple dialogs.
        AlertDialog(
            // onDismissRequest is called when the user tries to dismiss the dialog,
            // such as by pressing the back button or clicking outside the dialog.
            // Here, we set showDialog to false to close the dialog.
            onDismissRequest = { showDialog = false },

            // title defines the title of the dialog.
            title = { Text(text = "Add New Task") },

            // text defines the content of the dialog, which in this case
            // is a Column containing two OutlinedTextFields for task input.
            text = {
                Column {
                    // OutlinedTextField is a text input field with an outlined border.
                    // The value parameter holds the current text in the input field,
                    // and onValueChange is a callback that updates the state when the text changes.
                    OutlinedTextField(
                        value = newTaskTitle,
                        onValueChange = { newTaskTitle = it },
                        label = { Text("Title") } // The label that appears above the text field.
                    )
                    OutlinedTextField(
                        value = newTaskDescription,
                        onValueChange = { newTaskDescription = it },
                        label = { Text("Description") } // The label that appears above the text field.
                    )
                }
            },

            // confirmButton defines the button that confirms the action (e.g., adding the task).
            confirmButton = {
                // The Button component represents a clickable button.
                Button(onClick = {
                    // Checks if both the title and description are not empty before proceeding.
                    if (newTaskTitle.isNotEmpty() && newTaskDescription.isNotEmpty()) {
                        // Calls the addTask method in the ViewModel to add the new task.
                        viewModel.addTask(newTaskTitle, newTaskDescription)

                        // Closes the dialog by setting showDialog to false.
                        showDialog = false

                        // Resets the text fields to empty strings.
                        newTaskTitle = ""
                        newTaskDescription = ""
                    }
                }) {
                    // The text displayed on the button.
                    Text("Add")
                }
            },

            // dismissButton defines the button that cancels the action and closes the dialog.
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

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

                // Button for adding a new task.
                Button(onClick = { showDialog = true }) {
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