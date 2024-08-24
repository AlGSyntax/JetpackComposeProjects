package com.algsyntax.todojetpackcompose.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
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
fun TaskItem(task: Task, onTaskChecked: (Task) -> Unit) {
    // Lokale Kopie des isCompleted-Status zur Vermeidung von UI-Fehlern
    var isChecked by remember { mutableStateOf(task.isCompleted) }

    Row(modifier = Modifier.padding(8.dp)) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                isChecked = checked
                onTaskChecked(task.copy(isCompleted = checked))
            }
        )
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
