package com.remziakgoz.todolistwithcompose.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(

    @ColumnInfo("name")
    var toDoName: String?

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
