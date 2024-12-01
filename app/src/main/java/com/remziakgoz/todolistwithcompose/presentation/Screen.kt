package com.remziakgoz.todolistwithcompose.presentation

sealed class Screen(val route: String) {
    object ListScreen : Screen("list_screen")
    object ListAddScreen : Screen("list_add_screen")
}