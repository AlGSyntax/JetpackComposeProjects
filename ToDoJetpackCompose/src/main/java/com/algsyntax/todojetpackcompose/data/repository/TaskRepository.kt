package com.algsyntax.todojetpackcompose.data.repository

import android.util.Log
import com.algsyntax.jetpackcomposetodo.data.model.Task
import com.algsyntax.todojetpackcompose.data.sqlite.ToDoDao
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing tasks in the ToDo application.
 *
 * This class provides methods to retrieve, add, and complete tasks
 * using a Room database.
 */
class TaskRepository(private val toDoDao: ToDoDao) {


    /**
     * Retrieves the list of all tasks from the Room database.
     *
     * @return A Flow that emits a list of tasks.
     */
    fun getTasks(): Flow<List<Task>> {
        return toDoDao.getAll()
    }

    /**
     * Adds a new task to the Room database.
     *
     * @param task The task to be added.
     */
    suspend fun addTask(task: Task) {
        toDoDao.insertAll(listOf(task))
        Log.d("TaskRepository", "Aufgabe wurde hinzugefügt: ${task.title}")

    }

    /**
     * Marks the task with the given ID as completed.
     *
     * @param taskId The ID of the task to mark as completed.
     */
    suspend fun completeTask(task: Task) {
        toDoDao.update(task)
    }

    /**
     * Deletes a specific task from the Room database.
     *
     * @param task The task to be deleted.
     */
    suspend fun deleteTask(task: Task) {
        Log.d("TaskRepository", "Deleting task from DB: ${task.title}")
        toDoDao.delete(task)
    }
}



