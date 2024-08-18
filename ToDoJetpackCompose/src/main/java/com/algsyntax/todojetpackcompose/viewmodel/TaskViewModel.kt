package com.algsyntax.todojetpackcompose.viewmodel

import androidx.lifecycle.ViewModel
import com.algsyntax.jetpackcomposetodo.data.model.Task
import com.algsyntax.todojetpackcompose.data.repository.TaskRepository

/**
 * ViewModel for managing tasks in the ToDo application.
 *
 * This ViewModel interacts with the TaskRepository to perform actions
 * such as retrieving tasks, adding new tasks, and marking tasks as complete.
 */
class TaskViewModel : ViewModel() {
    // Instance of TaskRepository to manage task data.
    private val repository = TaskRepository()

    // LiveData or StateFlow could be used here for reactive updates.
    // Retrieves the list of tasks from the repository.
    val tasks = repository.getTasks()

    /**
     * Adds a new task to the repository.
     *
     * @param task The task to be added.
     */
    fun addTask(task: Task) {
        repository.addTask(task)
    }

    /**
     * Marks the task with the given ID as complete.
     *
     * @param taskId The ID of the task to be marked as complete.
     */
    fun completeTask(taskId: Int) {
        repository.completeTask(taskId)
    }
}