package com.remziakgoz.todolistwithcompose.di

import androidx.room.Room
import com.remziakgoz.todolistwithcompose.data.repository.TodoRepositoryImpl
import com.remziakgoz.todolistwithcompose.data.roomdb.ItemDatabase
import com.remziakgoz.todolistwithcompose.domain.repository.TodoRepository
import com.remziakgoz.todolistwithcompose.presentation.views.ItemViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            ItemDatabase::class.java,
            "Items").build()
    }

    single { get <ItemDatabase>().itemDao() }

    single<TodoRepository> {
        TodoRepositoryImpl(get())
    }

    viewModel {
        ItemViewModel(get())
    }
}