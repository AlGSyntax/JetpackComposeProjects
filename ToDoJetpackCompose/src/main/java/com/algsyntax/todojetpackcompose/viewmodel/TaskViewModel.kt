package com.algsyntax.todojetpackcompose.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.algsyntax.jetpackcomposetodo.data.model.Task
import com.algsyntax.todojetpackcompose.data.repository.TaskRepository
import com.algsyntax.todojetpackcompose.data.sqlite.ToDoDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing tasks in the ToDo application.
 *
 * This ViewModel interacts with the TaskRepository to perform actions
 * such as retrieving tasks, adding new tasks, marking tasks as complete,
 * deleting tasks, and clearing completed tasks. The tasks are exposed
 * as a StateFlow to allow for reactive updates in the UI.
 *
 * @param application The application context used for database initialization.
 */
class TaskViewModel(application: Application) : AndroidViewModel(application) {

    // Repository instance for handling task data operations.
    private val repository: TaskRepository

    // Private mutable StateFlow to hold the list of all tasks.
    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())

    // Public immutable StateFlow to expose the tasks for the UI to observe.
    val allTasks: StateFlow<List<Task>> = _allTasks.asStateFlow()

    init {
        // Initialize the TaskRepository with the ToDoDao from the Room database.
        val toDoDao = ToDoDatabase.getDatabase(application).toDoDao
        repository = TaskRepository(toDoDao)
        loadTasks()  // Load tasks from the repository at initialization.
    }

    /**
     * Loads tasks from the repository and updates the StateFlow.
     *
     * This function launches a coroutine to asynchronously collect tasks
     * from the repository and update the `_allTasks` StateFlow with the latest data.
     */
    private fun loadTasks() {
        viewModelScope.launch {
            repository.getTasks().collect { tasks ->
                _allTasks.value = tasks
            }
        }
    }

    /**
     * Deletes a task from the repository and updates the StateFlow.
     *
     * This function deletes a task from the database via the repository.
     * After deletion, it reloads the tasks to ensure the UI is updated.
     *
     * @param task The task to be deleted.
     */
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            loadTasks()  // Reload tasks after deleting a task.
        }
    }

    /**
     * Clears all completed tasks from the repository and updates the StateFlow.
     *
     * This function filters out completed tasks from the `_allTasks` StateFlow
     * and deletes them from the repository. After clearing, it reloads the tasks
     * to ensure the UI reflects the changes.
     */
    fun clearCompletedTasks() {
        viewModelScope.launch {
            val completedTasks = _allTasks.value.filter { it.isCompleted }
            if (completedTasks.isEmpty()) {
                Log.d("TaskViewModel", "No completed tasks to clear.")
            } else {
                completedTasks.forEach {
                    Log.d("TaskViewModel", "Deleting task: ${it.title}")
                    repository.deleteTask(it)
                }
            }
            loadTasks()  // Reload tasks after clearing completed ones.
        }
    }

    /**
     * Adds a new task to the repository and triggers the data update.
     *
     * This function creates a new `Task` object with the provided title and description,
     * then adds it to the repository. After adding the task, it reloads the tasks
     * to update the UI.
     *
     * @param title The title of the task to be added.
     * @param description The description of the task to be added.
     */
    fun addTask(title: String, description: String) {
        val newTask = Task(
            id = 0,  // Room will auto-generate the ID.
            title = title,
            description = description,
            isCompleted = false
        )
        viewModelScope.launch {
            repository.addTask(newTask)
            loadTasks()  // Reload tasks after adding a new task.
        }
    }

    /**
     * Toggles the completion status of the task and updates the repository.
     *
     * This function inverts the `isCompleted` status of the given task and updates it
     * in the repository. After updating the task, it reloads the tasks to reflect the changes in the UI.
     *
     * @param task The task to be toggled.
     */
    fun completeTask(task: Task, iscompleted: Boolean) {
        val updatedTask = task.copy(isCompleted = iscompleted)
        viewModelScope.launch {
            Log.d("TaskViewModel", "Updating task: ${updatedTask.title}, isCompleted: ${updatedTask.isCompleted}")
            repository.completeTask(updatedTask)
            loadTasks()  // Reload tasks after updating a task.
        }
    }
}