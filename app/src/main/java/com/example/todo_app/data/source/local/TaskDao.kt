package com.example.todo_app.data.source.local

import androidx.room.*
import com.example.todo_app.data.Task

@Dao
interface TaskDao {

  @Query("SELECT * FROM tasks")
  suspend fun getTasks(): List<Task>

  @Query("SELECT * FROM tasks WHERE id = :id")
  suspend fun getTaskById(id: String): Task

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTask(task: Task)

  @Update
  suspend fun updateTask(task: Task)

  @Query("UPDATE tasks SET completed = :completed WHERE id = :id")
  suspend fun updateCompleted(id: String, completed: Boolean)

  @Query("DELETE FROM tasks WHERE id = :id")
  suspend fun deleteTaskById(id: String): Int

  @Query("DELETE FROM tasks")
  suspend fun deleteTasks()

  @Query("DELETE FROM tasks WHERE completed = 1")
  suspend fun deleteCompletedTasks(): Int
}