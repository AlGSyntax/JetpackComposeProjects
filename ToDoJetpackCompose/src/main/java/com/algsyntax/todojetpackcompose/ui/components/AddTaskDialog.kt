package com.algsyntax.todojetpackcompose.ui.components




import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Ein Composable, das einen Dialog zum Hinzufügen einer neuen Aufgabe im terminalähnlichen Stil anzeigt.
 *
 * @param showDialog Ein boolescher Flag zur Steuerung der Sichtbarkeit des Dialogs.
 * @param onDismiss Eine Callback-Funktion, die aufgerufen wird, wenn der Dialog abgelehnt wird.
 * @param onAddTask Eine Callback-Funktion, die aufgerufen wird, wenn die Aufgabe hinzugefügt wird.
 *                  Sie erhält den Titel und die Beschreibung der neuen Aufgabe.
 */
@Composable
fun AddTaskDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAddTask: (String, String) -> Unit
) {
    if (showDialog) {
        // Lokale Zustandsvariablen zur Speicherung der Benutzereingaben
        var newTaskTitle by remember { mutableStateOf(TextFieldValue("")) }
        var newTaskDescription by remember { mutableStateOf(TextFieldValue("")) }

        // Angepasstes AlertDialog für den terminalähnlichen Stil
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = Color.Black, // Setzt den Hintergrund des Dialogs auf schwarz
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF002200))
                        .padding(8.dp)
                        .border(BorderStroke(2.dp, Color(0xFF00FF00)))
                ) {
                    BasicText(
                        text = "Neue Aufgabe hinzufügen",
                        style = TextStyle(
                            color = Color(0xFF00FF00),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .background(Color(0xFF002200))
                        .padding(8.dp)
                ) {
                    // Eingabefeld für den Aufgabentitel
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(1.dp, Color(0xFF00FF00)))
                            .background(Color(0xFF001100))
                            .padding(4.dp)
                    ) {
                        BasicTextField(
                            value = newTaskTitle,
                            onValueChange = { newTaskTitle = it },
                            textStyle = TextStyle(
                                color = Color(0xFF00FF00),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 16.sp
                            ),
                            decorationBox = { innerTextField ->
                                if (newTaskTitle.text.isEmpty()) {
                                    BasicText(
                                        text = "Titel",
                                        style = TextStyle(
                                            color = Color(0xFF00FF00).copy(alpha = 0.5f),
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 16.sp
                                        )
                                    )
                                }
                                innerTextField()
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Eingabefeld für die Aufgabenbeschreibung
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .border(BorderStroke(1.dp, Color(0xFF00FF00)))
                            .background(Color(0xFF001100))
                            .padding(4.dp)
                    ) {
                        BasicTextField(
                            value = newTaskDescription,
                            onValueChange = { newTaskDescription = it },
                            textStyle = TextStyle(
                                color = Color(0xFF00FF00),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 16.sp
                            ),
                            decorationBox = { innerTextField ->
                                if (newTaskDescription.text.isEmpty()) {
                                    BasicText(
                                        text = "Beschreibung",
                                        style = TextStyle(
                                            color = Color(0xFF00FF00).copy(alpha = 0.5f),
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 16.sp
                                        )
                                    )
                                }
                                innerTextField()
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
                            val title = newTaskTitle.text.trim()
                            val description = newTaskDescription.text.trim()
                            if (title.isNotEmpty() && description.isNotEmpty()) {
                                onAddTask(title, description)
                            }
                        }
                        .background(Color(0xFF00FF00))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .border(BorderStroke(1.dp, Color(0xFF00FF00)))
                ) {
                    BasicText(
                        text = "Hinzufügen",
                        style = TextStyle(
                            color = Color(0xFF002200),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            dismissButton = {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { onDismiss() }
                        .background(Color(0xFF004400))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .border(BorderStroke(1.dp, Color(0xFF00FF00)))
                ) {
                    BasicText(
                        text = "Abbrechen",
                        style = TextStyle(
                            color = Color(0xFF00FF00),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        )
    }
}