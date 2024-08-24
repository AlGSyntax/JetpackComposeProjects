package com.algsyntax.todojetpackcompose.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.algsyntax.jetpackcomposetodo.data.model.Task
import com.algsyntax.todojetpackcompose.data.repository.TaskRepository
import com.algsyntax.todojetpackcompose.data.sqlite.ToDoDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing tasks in the ToDo application.
 *
 * This ViewModel interacts with the TaskRepository to perform actions
 * such as retrieving tasks, adding new tasks, and marking tasks as complete.
 * The tasks are exposed as a Flow to allow for reactive updates in the UI.
 */
class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allTasks: Flow<List<Task>>

    init {
        val toDoDao = ToDoDatabase.getDatabase(application).toDoDao
        repository = TaskRepository(toDoDao)
        allTasks = repository.getTasks()
    }

    /**
     * Adds a new task to the repository and triggers the data update.
     *
     * @param title The title of the task to be added.
     * @param description The description of the task to be added.
     */
    fun addTask(title: String, description: String) {
        val newTask = Task(
            id = 0,  // Room will auto-generate the ID
            title = title,
            description = description,
            isCompleted = false
        )
        viewModelScope.launch {
            repository.addTask(newTask)
        }
    }

    /**
     * Toggles the completion status of the task and updates the repository.
     *
     * @param task The task to be toggled.
     */
    fun completeTask(task: Task) {
        val updatedTask = task.copy(isCompleted = !task.isCompleted)
        viewModelScope.launch {
            repository.completeTask(updatedTask)
        }
    }
}