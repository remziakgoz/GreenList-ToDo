package com.remziakgoz.todolistwithcompose.data.util

import android.content.Context

object SharedPreferencesHelper {

    private const val PREF_NAME = "ToDoApp"

    fun saveCheckedState(context: Context, itemId: Int, isChecked: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("item_$itemId", isChecked).apply()
    }

    fun getCheckedState(context: Context, itemId: Int): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("item_$itemId", false)
    }
}