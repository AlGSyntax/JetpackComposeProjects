package com.algsyntax.todojetpackcompose

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.algsyntax.todojetpackcompose.ui.screens.TaskListScreen
import com.algsyntax.todojetpackcompose.ui.theme.ToDoJetpackComposeTheme
import com.algsyntax.todojetpackcompose.viewmodel.TaskViewModel


/**
 * The main activity of the ToDoJetpackCompose app.
 *
 * This activity sets the content view using Jetpack Compose and applies the app's theme.
 * It uses a Scaffold layout to manage the structure of the UI and passes padding values
 * from the Scaffold to the TaskListScreen composable.
 *
 * Additionally, it sets the FLAG_SECURE flag to prevent screenshots and screen recordings,
 * enhancing the security of user data.
 */
class MainActivity : ComponentActivity() {
    // ViewModel instance for managing the tasks
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Prevents screenshots and screen recordings for security reasons
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        setContent {
            ToDoJetpackComposeTheme {
                Scaffold { paddingValues ->
                    // Pass the padding values to the TaskListScreen composable
                    TaskListScreen(
                        viewModel = taskViewModel,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}