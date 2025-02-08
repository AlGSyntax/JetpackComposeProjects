package com.algsyntax.todojetpackcompose.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.algsyntax.jetpackcomposetodo.data.model.Task



/**
 * A composable function that displays an animated task item with a checkbox and delete button.
 *
 * This function takes a Task object and displays its title along with a checkbox and a delete button.
 * The checkbox reflects the completion status of the task and triggers a callback
 * when its state changes. The delete button triggers a callback when clicked to delete the task.
 * The task item can be expanded to show more details when clicked.
 *
 * @param task The task to display.
 * @param onTaskChecked Callback function that is triggered when the checkbox is checked or unchecked.
 *                      It provides the updated Task object with the new checked state.
 * @param onDeleteTask Callback function that is triggered when the delete button is clicked.
 *                     It provides the Task object that should be deleted.
 */
@Composable
fun TaskItem(task: Task, onTaskChecked: (Task) -> Unit, onDeleteTask: (Task) -> Unit) {
    // Local state to track if the task item is expanded
    var isExpanded by remember { mutableStateOf(false) }

    /**
     * LocalConfiguration provides access to configuration data such as the screen size,
     * orientation, font scale, etc., for the device the app is running on.
     * Here, we use it to dynamically get the screen height and width in dp (density-independent pixels).
     * This allows the TaskItem to adjust its size based on the device's screen dimensions.
     */
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // Card height and width animations based on the expanded state
    val cardHeight by animateDpAsState(
        targetValue = if (isExpanded) screenHeight / 2 else 100.dp,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "CardHeightAnimation"  // Label for the height of the card
    )

    val cardWidth by animateDpAsState(
        targetValue = if (isExpanded) screenWidth - 32.dp else screenWidth - 64.dp,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "CardWidthAnimation"  // Label for the width of the card
    )

    // Local copy of the isCompleted status to avoid UI issues with direct state changes.
    var isChecked by remember { mutableStateOf(task.isCompleted) }

    // Card layout with animations for width and height
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = cardWidth, height = cardHeight)
            .clickable { isExpanded = !isExpanded } // Toggle expanded state on click
            .border(1.dp, Color.Green), // Green border for terminal-like styling
             colors = CardDefaults.cardColors(containerColor = Color.Black) // Sets the background of the card to black
    ) {
        // Column to display task title and description (if expanded)
        Column(modifier = Modifier
            .background(Color.Black) // Ensure the content inside the card is also black
            .padding(8.dp)) {
            // Row layout arranges the checkbox, text, and delete button in a horizontal line.
            Row(modifier = Modifier

                .padding(8.dp)
                .background(Color.Black) // Ensure the row background is black
            ) {
                // Checkbox that reflects the task's completion status.
                Checkbox(
                    checked = isChecked, // Sets the current state of the checkbox (checked or unchecked)
                    onCheckedChange = { checked->
                        isChecked = checked // Updates the local state when the checkbox status changes
                        // Calls the callback function to update the parent state with the new value
                        onTaskChecked(task.copy(isCompleted = checked))
                    },
                    // Customizes the colors of the checkbox to match the terminal-like style
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Green,     // Color of the checkbox when checked (green border and background)
                        uncheckedColor = Color.Green,   // Color of the checkbox when unchecked (green border)
                        checkmarkColor = Color.Black    // Color of the checkmark inside the checkbox (black to stand out from the green background)
                    )
                )
                // Displays the task's title next to the checkbox.
                Text(text = task.title, modifier = Modifier.padding(start = 8.dp), style = TextStyle(color = Color.Green, fontFamily = FontFamily.Monospace))

                // Spacer to push the delete button to the right end of the Row.
                Spacer(modifier = Modifier.weight(1f))

                // Delete button to remove the task.
                IconButton(onClick = { onDeleteTask(task) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task",
                        tint = Color.Green) // Green icon for terminal feel)
                }
            }

            // Show the task description if the item is expanded
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = task.description, modifier = Modifier.padding(start = 8.dp), style = TextStyle(color = Color.White, fontFamily = FontFamily.Monospace))
            }
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
    val sampleTask = Task(id = 1, title = "Sample Task", description = "This is a sample task description", isCompleted = false)

    // Renders the TaskItem with the sample task.
    // The onTaskChecked and onDeleteTask lambdas are provided with empty implementations for the preview.
    TaskItem(
        task = sampleTask,
        onTaskChecked = {},
        onDeleteTask = {}
    )
}