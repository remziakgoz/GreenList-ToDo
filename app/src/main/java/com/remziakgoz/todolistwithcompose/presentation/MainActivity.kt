package com.remziakgoz.todolistwithcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.remziakgoz.todolistwithcompose.presentation.ui.theme.ToDoListWithComposeTheme
import com.remziakgoz.todolistwithcompose.presentation.views.AddItemScreen
import com.remziakgoz.todolistwithcompose.presentation.views.ItemList
import com.remziakgoz.todolistwithcompose.presentation.views.ItemViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<ItemViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getItemList()
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            ToDoListWithComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.ListScreen.route
                        ) {
                            composable(route = Screen.ListScreen.route) {
                                viewModel.getItemList()
                                ItemList(navController =  navController, viewModel =  viewModel)
                            }

                            composable(route = Screen.ListAddScreen.route) {
                                AddItemScreen { item ->
                                    viewModel.saveItem(item)
                                    navController.navigate(route = Screen.ListScreen.route)
                                }
                            }


                        }
                    }
                }
            }
        }
    }
}