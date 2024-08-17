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
 * @param task The task to display.
 * @param onTaskChecked Callback function that is triggered when the checkbox is checked or unchecked.
 */
@Composable
fun TaskItem(task: Task, onTaskChecked: (Boolean) -> Unit) {
    Row(modifier = Modifier.padding(8.dp)) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = onTaskChecked
        )
        Text(text = task.title, modifier = Modifier.padding(start = 8.dp))
    }
}

/**
 * Preview function for TaskItem.
 * This function provides a sample task for preview purposes.
 */
@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    val sampleTask = Task(id = 1, title = "Sample Task", description = "This is a sample task", isCompleted = false)
    TaskItem(task = sampleTask, onTaskChecked = {})
}