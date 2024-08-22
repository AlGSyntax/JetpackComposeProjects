package com.algsyntax.todojetpackcompose.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.algsyntax.jetpackcomposetodo.data.model.Task

/**
 * A composable function that displays a task item with a checkbox.
 *
 * This function takes a Task object and displays its title along with a checkbox.
 * The checkbox reflects the completion status of the task and triggers a callback
 * when its state changes.
 *
 * @param task The task to display.
 * @param onTaskChecked Callback function that is triggered when the checkbox is checked or unchecked.
 *                      It provides the new checked state as a Boolean.
 */
@Composable
fun TaskItem(task: Task, onTaskChecked: (Boolean) -> Unit) {
    // Row layout arranges the checkbox and text in a horizontal line
    Row(modifier = Modifier.padding(8.dp)) {
        // Checkbox displays the completion status of the task
        Checkbox(
            checked = task.isCompleted, // Sets the checkbox state based on the task's completion status
            onCheckedChange = { isChecked ->
                // Trigger the callback with the new checked state when the checkbox is toggled
                onTaskChecked(isChecked)
            }
        )
        // Displays the title of the task next to the checkbox
        Text(text = task.title, modifier = Modifier.padding(start = 8.dp))
    }
}

/**
 * Preview function for TaskItem.
 * This function provides a sample task for preview purposes.
 *
 * The preview shows how the TaskItem would look with a sample task.
 * It helps in visualizing the UI during development.
 */
@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    // Creates a sample task for previewing the TaskItem composable
    val sampleTask = Task(id = 1, title = "Sample Task", description = "This is a sample task", isCompleted = false)

    // Renders the TaskItem with the sample task
    // The onTaskChecked lambda is left empty as it's not used in the preview
    TaskItem(task = sampleTask, onTaskChecked = {})
}