package com.algsyntax.todojetpackcompose.data.repository

import com.algsyntax.jetpackcomposetodo.data.model.Task

/**
 * Repository for managing tasks in the ToDo application.
 *
 * This class provides methods to retrieve, add, and complete tasks.
 */
class TaskRepository {
    private val tasks = mutableListOf<Task>()

    /**
     * Retrieves the list of all tasks.
     *
     * @return A list of tasks.
     */
    fun getTasks(): List<Task> {
        return tasks
    }

    /**
     * Adds a new task to the repository.
     *
     * @param task The task to be added.
     */
    fun addTask(task: Task) {
        tasks.add(task)
    }

    /**
     * Marks the task with the given ID as completed.
     *
     * @param taskId The ID of the task to mark as completed.
     */
    fun completeTask(taskId: Int) {
        tasks.find { it.id == taskId }?.let {
            it.isCompleted = true
        }
    }
}