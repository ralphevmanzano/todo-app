package com.example.todo_app.data

import com.example.android.architecture.blueprints.todoapp.data.Result

interface TasksRepository {

  suspend fun getTasks(): Result<List<Task>>

  suspend fun getTask(id: String): Result<Task>

  suspend fun saveTask(task: Task)

  suspend fun updateTask(task: Task)

  suspend fun completeTask(task: Task)

  suspend fun activateTask(task: Task)

  suspend fun activateTask(id: String)

  suspend fun clearCompletedTasks()

  suspend fun deleteAllTasks()

  suspend fun deleteTask(id: String)
}