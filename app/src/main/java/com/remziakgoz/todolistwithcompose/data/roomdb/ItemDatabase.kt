package com.remziakgoz.todolistwithcompose.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.remziakgoz.todolistwithcompose.domain.model.Item

@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}