package com.algsyntax.todojetpackcompose.data.sqlite



import androidx.room.*
import com.algsyntax.jetpackcomposetodo.data.model.Task
import kotlinx.coroutines.flow.Flow

// Defines the Data Access Object (DAO) for ToDo database operations.
// @Dao indicates that this is a DAO in the context of Room.
@Dao
interface ToDoDao {

    /**
     * Inserts a list of ToDos into the database. Existing ToDos are replaced.
     *
     * @param todos: The list of ToDos to be inserted.
     * @onConflict: onConflict strategy defines the behavior in case of a conflict.
     * Here, REPLACE means that existing entries will be overwritten.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todos: List<Task>)

    /**
     * Updates an existing ToDo in the database.
     *
     * @param todo: The ToDo object to be updated.
     */
    @Update
    suspend fun update(todo: Task)

    /**
     * Retrieves all ToDos from the database.
     * This method returns a Flow object containing the list of ToDos.
     * Flow allows for reactive updates to be observed automatically.
     *
     * @return: A Flow object with the list of all ToDos.
     */
    @Query("SELECT * FROM task_table")
    fun getAll(): Flow<List<Task>>

    /**
     * Deletes a specific ToDo from the database.
     *
     * @param todo: The ToDo object to be deleted.
     */
    @Delete
    suspend fun delete(todo: Task)

    /**
     * Deletes all ToDos from the database.
     * This method is used to clear the database, e.g., when data needs to be refreshed.
     */
    @Query("DELETE FROM task_table")
    suspend fun deleteAll()
}