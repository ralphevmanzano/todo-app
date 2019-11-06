package com.example.todo_app.ui.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.todo_app.LiveDataTestUtil
import com.example.todo_app.TestMainCoroutineRule
import com.example.todo_app.data.Task
import com.example.todo_app.data.TasksRepository
import com.example.todo_app.runBlocking
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.MatcherAssert.assertThat


@ExperimentalCoroutinesApi
class TasksViewModelTest {

  private lateinit var tasksViewModel: TasksViewModel
  private lateinit var tasksRepository: TasksRepository
  private lateinit var task1: Task
  private lateinit var task2: Task
  private lateinit var task3: Task

  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = TestMainCoroutineRule()

  // Executes each task synchronously using Architecture Components.
  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setupViewModel() {
    tasksRepository = mock{}
    tasksViewModel = TasksViewModel(tasksRepository, mainCoroutineRule.testDispatcher)

    task1 = Task("title1", "description1")
    task2 = Task("title2", "description2")
    task3 = Task("title3", "description3")
  }

  @Test
  fun clickOnFab_goToAddTask() {
    tasksViewModel.goToNewTask()

    val value = LiveDataTestUtil.getValue(tasksViewModel.newTaskEvent)
    assertThat(value.getContentIfNotHandled(), notNullValue())
  }

  @Test
  fun whenLoading_hasListOfTasks() = mainCoroutineRule.runBlocking {
    tasksRepository.stub {
      onBlocking { getTasks() }.doReturn(Result.Success(listOf(task1, task2, task3)))
    }

    tasksViewModel.loadTasks()

    verifyBlocking(tasksRepository){ getTasks() }

    val tasks = LiveDataTestUtil.getValue(tasksViewModel.tasks)
    assertThat(tasks.size, `is`(3))
    assertThat(tasks[0], `is`(task1))
    assertThat(tasks[1], `is`(task2))
    assertThat(tasks[2], `is`(task3))
  }

  @Test
  fun clickOnItem_goToAddEdit() {
    tasksViewModel.goToDetails(task1.id)

    val value = LiveDataTestUtil.getValue(tasksViewModel.detailsEvent)
    assertThat(value.getContentIfNotHandled(), `is`(task1.id))
  }

  @Test
  fun clickOnDelete_callsRepoDelete() {
    tasksViewModel.deleteTask(task1.id)

    verifyBlocking(tasksRepository){ deleteTask(task1.id) }
  }

  @Test
  fun clickCheckBox_callsOnCompleteItem() {
    tasksViewModel.completeTask(task1, true)

    verifyBlocking(tasksRepository){ completeTask(task1) }
  }
}