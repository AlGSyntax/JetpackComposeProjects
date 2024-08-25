package com.algsyntax.todojetpackcompose.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.algsyntax.jetpackcomposetodo.data.model.Task

/**
 * A composable function that displays a task item with a checkbox and delete button.
 *
 * This function takes a Task object and displays its title along with a checkbox and a delete button.
 * The checkbox reflects the completion status of the task and triggers a callback
 * when its state changes. The delete button triggers a callback when clicked to delete the task.
 *
 * @param task The task to display.
 * @param onTaskChecked Callback function that is triggered when the checkbox is checked or unchecked.
 *                      It provides the updated Task object with the new checked state.
 * @param onDeleteTask Callback function that is triggered when the delete button is clicked.
 *                     It provides the Task object that should be deleted.
 */
@Composable
fun TaskItem(task: Task, onTaskChecked: (Task) -> Unit, onDeleteTask: (Task) -> Unit) {
    // Local copy of the isCompleted status to avoid UI issues with direct state changes.
    var isChecked by remember { mutableStateOf(task.isCompleted) }

    // Row layout arranges the checkbox, text, and delete button in a horizontal line.
    Row(modifier = Modifier.padding(8.dp)) {
        // Checkbox that reflects the task's completion status.
        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                isChecked = checked
                // Trigger the callback with the updated task when the checkbox state changes.
                onTaskChecked(task.copy(isCompleted = checked))
            }
        )
        // Displays the task's title next to the checkbox.
        Text(text = task.title, modifier = Modifier.padding(start = 8.dp))

        // Spacer to push the delete button to the right end of the Row.
        Spacer(modifier = Modifier.weight(1f))

        // Delete button to remove the task.
        IconButton(onClick = { onDeleteTask(task) }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task")
        }
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
    // Creates a sample task for previewing the TaskItem composable.
    val sampleTask = Task(id = 1, title = "Sample Task", description = "This is a sample task", isCompleted = false)

    // Renders the TaskItem with the sample task.
    // The onTaskChecked and onDeleteTask lambdas are provided with empty implementations for the preview.
    TaskItem(
        task = sampleTask,
        onTaskChecked = {},
        onDeleteTask = {}
    )
}