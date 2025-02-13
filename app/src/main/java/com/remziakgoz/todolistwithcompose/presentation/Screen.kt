package com.remziakgoz.todolistwithcompose.presentation

sealed class Screen(val route: String) {
    data object ListScreen : Screen("list_screen")
    data object ListAddScreen : Screen("list_add_screen")
}