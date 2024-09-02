package com.algsyntax.todojetpackcompose.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

/**
 * A Composable function that displays a dialog for adding a new task.
 *
 * This function shows a dialog with input fields for the task title and description,
 * along with buttons to confirm or dismiss the dialog.
 *
 * @param showDialog A boolean flag to control the visibility of the dialog.
 * @param onDismiss A callback function to be invoked when the dialog is dismissed.
 * @param onAddTask A callback function to be invoked when the task is added.
 *                  It provides the title and description of the new task.
 */
@Composable
fun AddTaskDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAddTask: (String, String) -> Unit
) {
    // Check if the dialog should be displayed
    if (showDialog) {
        // Local state variables to store the user input for the task title and description
        var newTaskTitle by remember { mutableStateOf("") }
        var newTaskDescription by remember { mutableStateOf("") }

        // Composable AlertDialog to display the dialog UI
        AlertDialog(
            onDismissRequest = onDismiss, // Triggered when the user attempts to dismiss the dialog
            title = {
                Text(
                    text = "Add New Task",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            text = {
                // Column to stack the input fields vertically
                Column {
                    // Input field for the task title
                    OutlinedTextField(
                        value = newTaskTitle,
                        onValueChange = { newTaskTitle = it },
                        label = { Text("Title", color = MaterialTheme.colorScheme.primary) }
                    )
                    // Input field for the task description
                    OutlinedTextField(
                        value = newTaskDescription,
                        onValueChange = { newTaskDescription = it },
                        label = { Text("Description", color = MaterialTheme.colorScheme.primary) }
                    )
                }
            },
            confirmButton = {
                // Button to confirm the addition of the task
                Button(
                    onClick = {
                        if (newTaskTitle.isNotEmpty() && newTaskDescription.isNotEmpty()) {
                            onAddTask(newTaskTitle, newTaskDescription) // Invoke the callback to add the task
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Add", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                // Button to dismiss the dialog without adding a task
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onSecondary)
                }
            }
        )
    }
}

/**
 * Preview function for the AddTaskDialog Composable.
 *
 * This function provides a preview of the AddTaskDialog Composable
 * with a sample task and shows how the dialog would look in the UI.
 */
@Preview(showBackground = true)
@Composable
fun AddTaskDialogPreview() {
    AddTaskDialog(
        showDialog = true,
        onDismiss = {},
        onAddTask = { _, _ -> /* Preview callback */ }
    )
}