package com.remziakgoz.todolistwithcompose.presentation.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import com.remziakgoz.todolistwithcompose.domain.model.Item

@Composable
fun AddItemScreen(
    modifier: Modifier = Modifier,
    saveFunction: (item: Item) -> Unit
) {
    val itemName = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    // Dark mode kontrolü
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF7B7B7B) else Color.White
    val textColor = if (isDarkTheme) Color(0xFF90EE90) else Color(0xFF006400)
    val placeholderColor = if (isDarkTheme) Color(0xFF7FFF00) else Color(0xFF008000)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .imePadding()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = itemName.value,
                placeholder = {
                    Text(
                        "Enter ToDo",
                        color = placeholderColor,
                        textAlign = TextAlign.Center
                    )
                },
                onValueChange = {
                    itemName.value = it
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val itemToInsert = Item(toDoName = itemName.value)
                    if (itemName.value.isEmpty() || itemName.value.isBlank()) {
                        Toast.makeText(context, "Please Enter ToDo", Toast.LENGTH_SHORT).show()
                    } else {
                        saveFunction(itemToInsert)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDarkTheme) Color(0xFF32CD32) else Color(0xFF00CC00),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Add")
            }
        }
    }
}
