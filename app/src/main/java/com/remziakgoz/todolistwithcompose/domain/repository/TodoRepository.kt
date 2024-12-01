package com.remziakgoz.todolistwithcompose.domain.repository

import com.remziakgoz.todolistwithcompose.domain.model.Item

interface TodoRepository {

    suspend fun getItemList(): List<Item>
    suspend fun saveItem(item: Item)
    suspend fun deleteItem(item: Item)

}