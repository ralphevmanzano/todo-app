package com.example.todo_app.data

import android.util.Log
import com.example.android.architecture.blueprints.todoapp.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class DefaultTasksRepository @Inject constructor(
  private val tasksLocalDataSource: TasksDataSource,
  @Named("io") private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TasksRepository {

  override suspend fun getTasks(): Result<List<Task>> {
    return withContext(dispatcher) {
      val tasks = fetchTasks()
      Log.d("tasks", tasks.toString())
      (tasks as? Result.Success)?.let {
        Log.d("tasks1", it.data.toString())
        if (it.data.isNotEmpty()) {
          return@withContext Result.Success(it.data)
        }
        return@withContext Result.Error(Exception("Illegal state: empty"))
      }

      return@withContext Result.Error(Exception("Illegal state"))
    }
  }

  private suspend fun fetchTasks(): Result<List<Task>> {

    val tasks = tasksLocalDataSource.getTasks()
    if (tasks is Result.Success) return tasks

    return Result.Error(Exception("Error fetching tasks"))
  }

  override suspend fun getTask(id: String): Result<Task> {
    val task = tasksLocalDataSource.getTask(id)
    if (task is Result.Success) return task

    return Result.Error(Exception("Error fetching task"))
  }

  override suspend fun saveTask(task: Task) {
    tasksLocalDataSource.saveTask(task)
  }

  override suspend fun updateTask(task: Task) {
    tasksLocalDataSource.updateTask(task)
  }

  override suspend fun completeTask(task: Task) {
    tasksLocalDataSource.completeTask(task)
  }

  override suspend fun activateTask(task: Task) {
    tasksLocalDataSource.activateTask(task)
  }

  override suspend fun activateTask(id: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun clearCompletedTasks() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun deleteAllTasks() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun deleteTask(id: String) {
    tasksLocalDataSource.deleteTask(id)
  }

}