package com.algsyntax.todojetpackcompose.data.sqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.algsyntax.jetpackcomposetodo.data.model.Task

// Defines the database class for the app, representing a Room database.
// @Database indicates that this is a Room database and lists all the entities.
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    // Abstract method that provides the ToDoDao for accessing the database.
    abstract val toDoDao: ToDoDao

    companion object {
        // Instance of ToDoDatabase for Singleton purposes.
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        /**
         * Returns the Singleton instance of ToDoDatabase.
         *
         * @param context: The application context, needed for database creation.
         * @return: The Singleton instance of ToDoDatabase.
         */
        fun getDatabase(context: Context): ToDoDatabase {
            return INSTANCE ?: synchronized(this) {
                // Correctly building the Room database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}