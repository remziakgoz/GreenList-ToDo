package com.remziakgoz.todolistwithcompose.presentation.views

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remziakgoz.todolistwithcompose.domain.model.Item
import com.remziakgoz.todolistwithcompose.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: TodoRepository) : ViewModel() {

    val itemList = mutableStateOf<List<Item>>(listOf())


    fun getItemList() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = repository.getItemList()
            itemList.value = items
        }
    }

    fun saveItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
           repository.deleteItem(item)
            getItemList()
        }

    }




}