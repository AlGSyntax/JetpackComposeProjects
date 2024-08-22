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
     * Marks the task with the given ID as complete and updates the StateFlow.
     *
     * @param taskId The ID of the task to be marked as complete.
     */
    fun completeTask(taskId: Int) {
        repository.completeTask(taskId)
        _tasks.value = repository.getTasks() // Update the StateFlow with the updated task list.
    }
}