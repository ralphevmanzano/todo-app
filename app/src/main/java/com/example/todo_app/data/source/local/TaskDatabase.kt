package com.example.todo_app.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo_app.data.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {
  abstract fun taskDao(): TaskDao
}