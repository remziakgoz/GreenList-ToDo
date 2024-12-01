package com.remziakgoz.todolistwithcompose.data.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.remziakgoz.todolistwithcompose.domain.model.Item

@Dao
interface ItemDao {

    @Query("SELECT name, id FROM Item")
    suspend fun getItemWithNameAndId(): List<Item>

    @Insert
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(item: Item)

}