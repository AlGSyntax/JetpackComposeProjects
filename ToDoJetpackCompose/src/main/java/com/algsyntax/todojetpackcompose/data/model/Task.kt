package com.algsyntax.jetpackcomposetodo.data.model

/**
 * Represents a task in the ToDo application.
 *
 * @property id The unique identifier for the task.
 * @property title The title of the task.
 * @property description A brief description of the task.
 * @property isCompleted Indicates whether the task has been completed. Defaults to false.
 */
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    var isCompleted: Boolean = false
)