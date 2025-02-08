package com.algsyntax.todojetpackcompose

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import com.algsyntax.todojetpackcompose.ui.navigation.NavGraph
import com.algsyntax.todojetpackcompose.ui.theme.ToDoJetpackComposeTheme


/**
 * The main activity of the ToDoJetpackCompose app.
 *
 * This activity serves as the entry point for the application. It is responsible for:
 * 1. Setting up the content view using Jetpack Compose.
 * 2. Applying the app's custom theme (`ToDoJetpackComposeTheme`).
 * 3. Managing the navigation within the app using the Jetpack Compose Navigation library.
 * 4. Implementing security measures to prevent screenshots and screen recordings.
 * 5. Providing the application context to composable functions.
 *
 * The UI is structured using a `Scaffold` composable, which provides a basic layout structure
 * for Material Design apps. Padding values from the `Scaffold` are passed to the `NavGraph`
 * composable to ensure proper spacing of the content.
 */
class MainActivity : ComponentActivity() {




    /**
     * Called when the activity is starting.
     *
     * This is where most initialization should go: calling `setContentView` to inflate
     * the activity's UI, initializing data, etc.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down, this Bundle contains the data it most
     *                           recently supplied in `onSaveInstanceState`. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Prevents screenshots and screen recordings for security reasons.
        // This is done by setting the FLAG_SECURE flag on the window.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        // `setContent` is a Jetpack Compose function that sets the activity's content view.
        setContent {
            // `ToDoJetpackComposeTheme` applies the app's custom theme to the composable hierarchy.
            ToDoJetpackComposeTheme {
                // `rememberNavController` creates and remembers a NavController instance.
                // The NavController is used to manage navigation within the app.
                val navController = rememberNavController()
                // `LocalContext.current` provides the current Android Context.
                // This is used to pass the context to composable functions that might need it.
                val context = LocalContext.current

                // `Scaffold` provides a basic Material Design layout structure.
                // It handles the placement of common UI elements like top app bars,
                // bottom navigation, etc.
                Scaffold { paddingValues ->
                    // `Box` is a simple layout composable that allows you to stack elements on top of each other.
                    // Here, it's used to apply the padding values from the `Scaffold` to the content.
                    Box(modifier = Modifier.padding(paddingValues)) {
                        // `NavGraph` is a custom composable that defines the navigation graph for the app.
                        // It takes the `navController` and `context` as parameters.
                        NavGraph(
                            navController = navController,
                            context = context
                        )
                    }
                }
            }
        }
    }
}