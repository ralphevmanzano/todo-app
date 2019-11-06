package com.example.todo_app.data.source.local

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.todo_app.data.Task
import com.example.todo_app.data.TasksDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TasksLocalDataSource internal constructor(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TasksDataSource {

  override suspend fun getTasks(): Result<List<Task>> = withContext(ioDispatcher) {
    return@withContext try {
      Result.Success(taskDao.getTasks())
    } catch (e: Exception) {
      Result.Error(e)
    }
  }

  override suspend fun getTask(taskId: String): Result<Task> = withContext(ioDispatcher){
    return@withContext try {
      Result.Success(taskDao.getTaskById(taskId))
    } catch (e: Exception) {
      Result.Error(e)
    }
  }

  override suspend fun saveTask(task: Task) = withContext(ioDispatcher) {
    taskDao.insertTask(task)
  }

  override suspend fun updateTask(task: Task) {
    taskDao.updateTask(task)
  }
  override suspend fun completeTask(task: Task) {
    taskDao.updateCompleted(task.id, true)
  }

  override suspend fun completeTask(taskId: String) {
    taskDao.updateCompleted(taskId, true)
  }

  override suspend fun activateTask(task: Task) {
    taskDao.updateCompleted(task.id, false)
  }

  override suspend fun activateTask(taskId: String) {
    taskDao.updateCompleted(taskId, true)
  }

  override suspend fun clearCompletedTasks() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun deleteAllTasks() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun deleteTask(taskId: String) {
    taskDao.deleteTaskById(taskId)
  }

}