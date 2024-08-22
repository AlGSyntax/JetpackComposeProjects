package com.algsyntax.todojetpackcompose.viewmodel

import androidx.lifecycle.ViewModel
import com.algsyntax.jetpackcomposetodo.data.model.Task
import com.algsyntax.todojetpackcompose.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel for managing tasks in the ToDo application.
 *
 * This ViewModel interacts with the TaskRepository to perform actions
 * such as retrieving tasks, adding new tasks, and marking tasks as complete.
 * The tasks are exposed as a StateFlow to allow for reactive updates in the UI.
 */
class TaskViewModel : ViewModel() {

    // Instance of TaskRepository to manage task data.
    private val repository = TaskRepository()

    // StateFlow to hold the current list of tasks and provide reactive updates.
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        // Initialize the tasks from the repository.
        _tasks.value = repository.getTasks()
    }

    /**
     * Adds a new task to the repository and updates the StateFlow.
     *
     * @param title The title of the task to be added.
     * @param description The description of the task to be added.
     */
    fun addTask(title: String, description: String) {
        // Create a new Task with a generated ID.
        val newTask = Task(
            id = _tasks.value.size + 1,  // Simplified ID generation
            title = title,
            description = description,
            isCompleted = false
        )
        // Add the new task to the repository and update the StateFlow.
        repository.addTask(newTask)
        _tasks.value = repository.getTasks() // Update the StateFlow with the new task list.
    }

    /**
     * Toggles the completion status of the task with the given ID and updates the StateFlow.
     *
     * This method finds the task by its ID, toggles its completion status (i.e., if it was
     * completed, it will be marked as not completed, and vice versa), and then updates
     * the repository and the StateFlow to reflect this change.
     *
     * @param taskId The ID of the task to be toggled.
     */
    fun completeTask(taskId: Int) {
        // Toggle the completion status of the task in the repository
        repository.completeTask(taskId)

        // Update the StateFlow with the latest list of tasks from the repository
        // This will trigger a re-composition of the UI to reflect the changes
        _tasks.value = _tasks.value.map { task ->
            if (task.id == taskId) {
                // Toggle the isCompleted state
                task.copy(isCompleted = !task.isCompleted)
            } else {
                task
            }
        }
    }
}