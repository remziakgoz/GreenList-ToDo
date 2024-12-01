package com.remziakgoz.todolistwithcompose.presentation.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.remziakgoz.todolistwithcompose.domain.model.Item

@Composable
fun AddItemScreen(modifier: Modifier = Modifier, saveFunction: (item: Item) -> Unit) {

    val itemName = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current




    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = itemName.value,
                placeholder = {
                    Text("Enter ToDo")
                }, onValueChange = {
                    itemName.value = it
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Button(onClick = {
                val itemToInsert = Item(toDoName = itemName.value)
                if (itemName.value.isEmpty() || itemName.value.isBlank()){
                    Toast.makeText(context, "Please Enter ToDo", Toast.LENGTH_SHORT).show()
                } else {
                saveFunction(itemToInsert)
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00FF00),
                    contentColor = Color.White
                )
                ) {
                Text(text = "Add")
            }


        }
    }


}