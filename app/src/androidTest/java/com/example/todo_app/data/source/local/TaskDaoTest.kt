package com.example.todo_app.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.todo_app.MainCoroutineRule
import com.example.todo_app.data.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest {

  private lateinit var database: TaskDatabase

  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun initDb() {
    // using an in-memory database because the information stored here disappears when the
    // process is killed
    database =
      Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext,
        TaskDatabase::class.java
      ).allowMainThreadQueries().build()
  }

  @After
  fun closeDb() = database.close()

  @Test
  fun insertTask_getById() = runBlockingTest {
    val task = Task("title", "desc")
    database.taskDao().insertTask(task)

    val savedTask = database.taskDao().getTaskById(task.id)

    assertThat(savedTask as Task, notNullValue())
    assertThat(savedTask.id, `is` (task.id))
    assertThat(savedTask.title, `is` (task.title))
    assertThat(savedTask.description, `is` (task.description))
    assertThat(savedTask.isCompleted, `is` (task.isCompleted))
  }

  @Test
  fun insertTask_replaceOnConflict() = runBlockingTest {
    val task = Task("title", "description")
    database.taskDao().insertTask(task)

    val task2 = Task("title2", "description2", true, task.id)
    database.taskDao().insertTask(task2)

    val savedTask = database.taskDao().getTaskById(task.id)
    assertThat(savedTask?.id, `is` (task.id))
    assertThat(savedTask?.title, `is` (task2.title))
    assertThat(savedTask?.description, `is` (task2.description))
    assertThat(savedTask?.isCompleted, `is` (task2.isCompleted))
  }

  @Test
  fun insertTasks_getTasks() = runBlockingTest {
    val task1 = Task("title1", "desciption1")
    val task2 = Task("title2", "desciption2")

    database.taskDao().insertTask(task1)
    database.taskDao().insertTask(task2)

    val savedTasks = database.taskDao().getTasks()

    assertThat(savedTasks.size, `is`(2))
    assertThat(savedTasks[0].id, `is`(task1.id))
    assertThat(savedTasks[0].title, `is`(task1.title))
    assertThat(savedTasks[0].description, `is`(task1.description))
    assertThat(savedTasks[0].isCompleted, `is`(task1.isCompleted))
    assertThat(savedTasks[1].id, `is`(task2.id))
    assertThat(savedTasks[1].title, `is`(task2.title))
    assertThat(savedTasks[1].description, `is`(task2.description))
    assertThat(savedTasks[1].isCompleted, `is`(task2.isCompleted))
  }

  @Test
  fun updateTask_getById() = runBlockingTest {
    val task1 = Task("title1", "description1")
    database.taskDao().insertTask(task1)

    val task2 = Task("task2", "description2", true, task1.id)
    database.taskDao().updateTask(task2)

    val savedTask = database.taskDao().getTaskById(task1.id)

    assertThat(savedTask?.id, `is`(task2.id))
    assertThat(savedTask?.title, `is`(task2.title))
    assertThat(savedTask?.description, `is`(task2.description))
    assertThat(savedTask?.isCompleted, `is`(task2.isCompleted))
  }

  @Test
  fun updateCompleted_getById() = runBlockingTest {
    val task1 = Task("title1", "description1")
    database.taskDao().insertTask(task1)

    database.taskDao().updateCompleted(task1.id, true)

    val savedTask = database.taskDao().getTaskById(task1.id)

    assertThat(savedTask?.id, `is`(task1.id))
    assertThat(savedTask?.title, `is`(task1.title))
    assertThat(savedTask?.description, `is`(task1.description))
    assertThat(savedTask?.isCompleted, `is`(true))
  }

  @Test
  fun deleteTaskById_getTasks() = runBlockingTest {
    val task = Task("title", "description")
    database.taskDao().insertTask(task)

    database.taskDao().deleteTaskById(task.id)

    val savedTasks = database.taskDao().getTasks()
    assertThat(savedTasks.isEmpty(), `is`(true))
  }

  @Test
  fun deleteTasks_getTasks() = runBlockingTest {
    val task1 = Task("title1", "description1")
    val task2 = Task("title2", "description2")

    database.taskDao().insertTask(task1)
    database.taskDao().insertTask(task2)
    database.taskDao().deleteTasks()

    val savedTasks = database.taskDao().getTasks()

    assertThat(savedTasks.isEmpty(), `is`(true))
  }

  @Test
  fun deleteCompletedTasks_getTasks() = runBlockingTest {
    val completedTask = Task("title1", "description1", true)
    val task = Task("title", "description")

    database.taskDao().insertTask(completedTask)
    database.taskDao().insertTask(task)
    database.taskDao().deleteCompletedTasks()

    val savedTask = database.taskDao().getTasks()

    assertThat(savedTask.size, `is`(1))
    assertThat(savedTask[0].id, `is`(task.id))
    assertThat(savedTask[0].title, `is`(task.title))
    assertThat(savedTask[0].description, `is`(task.description))
    assertThat(savedTask[0].isCompleted, `is`(task.isCompleted))
  }

}