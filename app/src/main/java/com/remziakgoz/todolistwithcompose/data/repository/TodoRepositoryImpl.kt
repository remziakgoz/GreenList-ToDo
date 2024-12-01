package com.remziakgoz.todolistwithcompose.data.repository

import com.remziakgoz.todolistwithcompose.data.roomdb.ItemDao
import com.remziakgoz.todolistwithcompose.domain.model.Item
import com.remziakgoz.todolistwithcompose.domain.repository.TodoRepository

class TodoRepositoryImpl(private val itemDao: ItemDao) : TodoRepository {

    override suspend fun getItemList(): List<Item> = itemDao.getItemWithNameAndId()

    override suspend fun saveItem(item: Item) {
        itemDao.insert(item)
    }

    override suspend fun deleteItem(item: Item) {
        itemDao.delete(item)
    }
}