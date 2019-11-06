package com.example.todo_app.data

import com.example.android.architecture.blueprints.todoapp.data.Result

interface TasksDataSource {

  suspend fun getTasks(): Result<List<Task>>

  suspend fun getTask(taskId: String): Result<Task>

  suspend fun saveTask(task: Task)

  suspend fun updateTask(task: Task)

  suspend fun completeTask(task: Task)

  suspend fun completeTask(taskId: String)

  suspend fun activateTask(task: Task)

  suspend fun activateTask(taskId: String)

  suspend fun clearCompletedTasks()

  suspend fun deleteAllTasks()

  suspend fun deleteTask(taskId: String)
}