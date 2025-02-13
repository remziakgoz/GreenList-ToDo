package com.remziakgoz.todolistwithcompose.presentation.views

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remziakgoz.todolistwithcompose.data.util.SharedPreferencesHelper
import com.remziakgoz.todolistwithcompose.domain.model.Item
import com.remziakgoz.todolistwithcompose.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.remziakgoz.todolistwithcompose.util.NotificationHelper

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

    fun checkUncompleted(context: Context, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val items = repository.getItemList()
            val hasUncompleted = items.any { item ->
                !SharedPreferencesHelper.getCheckedState(context, item.id)
            }
            callback(hasUncompleted)
        }
    }

    fun updateItemStatus(context: Context, itemId: Int, isChecked: Boolean) {
        SharedPreferencesHelper.saveCheckedState(context, itemId, isChecked)
        checkUncompleted(context) { hasUncompleted ->
            val notificationHelper = NotificationHelper(context)
            if (hasUncompleted) {
                notificationHelper.scheduleNotificationAtIntervals(1 * 1000L, 0L)
            } else {
                notificationHelper.cancelScheduledNotifications()
            }
        }
    }
}