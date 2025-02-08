package com.algsyntax.todojetpackcompose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * `AddTaskDialog` is a Composable function that displays a modal dialog for entering a new task with a unique,
 * terminal-like aesthetic. It provides an interactive interface for inputting a task title and description,
 * and integrates with the application's task management system through callback functions.
 *
 * This dialog is part of a larger system of task management and is designed to be both functional and stylistically consistent
 * with the application's theme. The dark background and green text color are reminiscent of classic computer terminals,
 * aiming to give the user a sense of focus and simplicity.
 *
 * Parameters:
 * @param showDialog Boolean state controlling the visibility of the dialog, managed by the parent composable.
 * @param onDismiss Callback function triggered when the dialog is dismissed without adding a task.
 *                  This ensures that the dialog's visibility is properly managed and that any other necessary cleanup is performed.
 * @param onAddTask Callback function triggered when a new task is submitted. It passes the task's title and description back
 *                  to the parent composable for further processing, such as adding the task to the list and updating the UI.
 *
 * Usage:
 * This dialog should be invoked from a parent composable that manages tasks, where the showDialog state and the callbacks
 * are defined. The state and functions flow down to this dialog as parameters, allowing it to function seamlessly within
 * the larger application context.
 */
@Composable
fun AddTaskDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAddTask: (String, String) -> Unit
) {
    if (showDialog) {
        // Local state variables for storing user input. The use of `remember` ensures that the input state
        // is preserved over recompositions unless the dialog is dismissed and shown again.
        var newTaskTitle by remember { mutableStateOf(TextFieldValue("")) }
        var newTaskDescription by remember { mutableStateOf(TextFieldValue("")) }

        // Customized AlertDialog styled to resemble a terminal interface.
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = Color.Black, // Sets the dialog's background color to black for a terminal-like appearance.
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF002200)) // Dark green background for the title bar.
                        .padding(8.dp)
                        .border(BorderStroke(2.dp, Color(0xFF00FF00))) // Green border to highlight the title area.
                ) {
                    BasicText(
                        text = "Add New Task",
                        style = TextStyle(
                            color = Color(0xFF00FF00), // Bright green text color for clarity.
                            fontFamily = FontFamily.Monospace, // Uses monospace font to align with the terminal theme.
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .background(Color(0xFF002200)) // Continues the dark green background theme.
                        .padding(8.dp)
                ) {
                    // Input field for the task title with a custom styled TextField.
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(1.dp, Color(0xFF00FF00))) // Green border for the input field.
                            .background(Color(0xFF001100)) // Slightly darker green for the input background.
                            .padding(4.dp)
                    ) {
                        BasicTextField(
                            value = newTaskTitle,
                            onValueChange = { newTaskTitle = it },
                            textStyle = TextStyle(
                                color = Color(0xFF00FF00),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 16.sp
                            ),
                            decorationBox = { innerTextField ->
                                // Placeholder text logic to guide the user.
                                if (newTaskTitle.text.isEmpty()) {
                                    BasicText(
                                        text = "Title",
                                        style = TextStyle(
                                            color = Color(0xFF00FF00).copy(alpha = 0.5f),
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 16.sp
                                        )
                                    )
                                }
                                innerTextField()
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Input field for the task description.
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .border(BorderStroke(1.dp, Color(0xFF00FF00)))
                            .background(Color(0xFF001100))
                            .padding(4.dp)
                    ) {
                        BasicTextField(
                            value = newTaskDescription,
                            onValueChange = { newTaskDescription = it },
                            textStyle = TextStyle(
                                color = Color(0xFF00FF00),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 16.sp
                            ),
                            decorationBox = { innerTextField ->
                                // Placeholder text for description.
                                if (newTaskDescription.text.isEmpty()) {
                                    BasicText(
                                        text = "Description",
                                        style = TextStyle(
                                            color = Color(0xFF00FF00).copy(alpha = 0.5f),
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 16.sp
                                        )
                                    )
                                }
                                innerTextField()
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
                            val title = newTaskTitle.text.trim()
                            val description = newTaskDescription.text.trim()
                            if (title.isNotEmpty() && description.isNotEmpty()) {
                                onAddTask(title, description) // Triggers the onAddTask callback with the input data.
                            }
                        }
                        .background(Color(0xFF00FF00))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .border(BorderStroke(1.dp, Color(0xFF00FF00)))
                ) {
                    BasicText(
                        text = "Add",
                        style = TextStyle(
                            color = Color(0xFF002200),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            dismissButton = {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { onDismiss() } // Handles the dismiss action.
                        .background(Color(0xFF004400))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .border(BorderStroke(1.dp, Color(0xFF00FF00)))
                ) {
                    BasicText(
                        text = "Cancel",
                        style = TextStyle(
                            color = Color(0xFF00FF00),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        )
    }
}